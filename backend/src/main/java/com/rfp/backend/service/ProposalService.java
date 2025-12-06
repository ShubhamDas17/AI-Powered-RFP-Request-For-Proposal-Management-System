package com.rfp.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rfp.backend.dto.BestProposalResponse;
import com.rfp.backend.dto.ProposalComparisonDTO;
import com.rfp.backend.model.Proposal;
import com.rfp.backend.model.RFP;
import com.rfp.backend.model.Vendor;
import com.rfp.backend.repository.ProposalRepository;
import com.rfp.backend.repository.RFPRepository;
import com.rfp.backend.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final VendorRepository vendorRepository;
    private final RFPRepository rfpRepository;
    private final AiService aiService;

    private final ObjectMapper mapper = new ObjectMapper();

    // ---------------------------------------------------------
    // SUBMIT PROPOSAL
    // ---------------------------------------------------------
    public Proposal submitProposal(Long vendorId, Long rfpId, String proposalText) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        RFP rfp = rfpRepository.findById(rfpId)
                .orElseThrow(() -> new RuntimeException("RFP not found"));

        Proposal proposal = new Proposal();
        proposal.setVendor(vendor);
        proposal.setRfp(rfp);
        proposal.setProposalText(proposalText);

        // Call AI evaluation
        String aiJson = aiService.evaluateProposal(
                rfp.getOriginalDescription(),
                proposalText,
                vendor.getName()
        );

        try {
            JsonNode node = mapper.readTree(aiJson);

            int score = node.has("score")
                    ? node.get("score").asInt()
                    : simpleScore(proposalText);

            proposal.setScore(score);

            String response = node.has("analysis")
                    ? node.get("analysis").asText()
                    : node.has("reason")
                    ? node.get("reason").asText()
                    : "No analysis provided";

            proposal.setStructuredResponse(response);

        } catch (Exception e) {
            proposal.setScore(simpleScore(proposalText));
            proposal.setStructuredResponse("AI parse failed; fallback used.");
        }

        return proposalRepository.save(proposal);
    }

    // ---------------------------------------------------------
    // GET PROPOSALS FOR RFP
    // ---------------------------------------------------------
    public List<Proposal> getProposalsForRFP(Long rfpId) {
        return proposalRepository.findByRfpId(rfpId);
    }

    // ---------------------------------------------------------
    // COMPARE PROPOSALS
    // ---------------------------------------------------------
    public List<ProposalComparisonDTO> compareProposals(Long rfpId) {

        RFP rfp = rfpRepository.findById(rfpId).orElseThrow();
        List<Proposal> proposals = proposalRepository.findByRfpId(rfpId);

        List<ProposalComparisonDTO> results = new ArrayList<>();

        for (Proposal p : proposals) {
            try {
                String aiJson = aiService.evaluateProposal(
                        rfp.getOriginalDescription(),
                        p.getProposalText(),
                        p.getVendor().getName()
                );

                JsonNode node = mapper.readTree(aiJson);

                int score = node.has("score")
                        ? node.get("score").asInt()
                        : simpleScore(p.getProposalText());

                String reason = node.has("analysis")
                        ? node.get("analysis").asText()
                        : node.has("reason")
                        ? node.get("reason").asText()
                        : "No reason provided";

                results.add(new ProposalComparisonDTO(
                        p.getVendor().getName(),
                        score,
                        reason
                ));
            } catch (Exception e) {
                results.add(new ProposalComparisonDTO(
                        p.getVendor().getName(),
                        simpleScore(p.getProposalText()),
                        "AI evaluation failed"
                ));
            }
        }

        results.sort((a, b) -> b.getScore().compareTo(a.getScore()));
        return results;
    }

    // ---------------------------------------------------------
    // BEST PROPOSAL — FORMATTED
    // ---------------------------------------------------------
    public BestProposalResponse getBestProposalFormatted(Long rfpId) {
        List<ProposalComparisonDTO> list = compareProposals(rfpId);

        ProposalComparisonDTO best = list.stream()
                .max(Comparator.comparing(ProposalComparisonDTO::getScore))
                .orElseThrow();

        return new BestProposalResponse(
                best.getVendor(),
                best.getScore(),
                best.getReason()
        );
    }

    // ---------------------------------------------------------
    // GET BEST (FIRST) PROPOSAL RAW
    // ---------------------------------------------------------
    public Proposal getBestProposal(Long rfpId) {
        return proposalRepository.findByRfpId(rfpId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    // ---------------------------------------------------------
    // SIMPLE FALLBACK SCORING (if AI fails)
    // ---------------------------------------------------------
    private int simpleScore(String text) {
        if (text == null) return 0;

        int s = 0;
        String lower = text.toLowerCase();

        if (lower.contains("20")) s += 30;
        if (lower.contains("16gb")) s += 30;
        if (lower.contains("25 day")) s += 20;
        if (lower.contains("49,000") || lower.contains("45000")) s += 20;

        return Math.min(100, s);
    }
}
