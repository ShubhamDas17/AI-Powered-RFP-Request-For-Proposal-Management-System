package com.rfp.backend.controller;

import com.rfp.backend.dto.BestProposalResponse;
import com.rfp.backend.dto.ProposalRequest;
import com.rfp.backend.dto.ProposalComparisonDTO;
import com.rfp.backend.model.Proposal;
import com.rfp.backend.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proposal")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProposalController {

    private final ProposalService proposalService;

    @PostMapping("/submit")
    public Proposal submitProposal(@RequestBody ProposalRequest request) {
        return proposalService.submitProposal(request.getVendorId(), request.getRfpId(), request.getProposalText());
    }

    @GetMapping("/rfp/{rfpId}")
    public List<Proposal> getProposalsForRFP(@PathVariable Long rfpId) {
        return proposalService.getProposalsForRFP(rfpId);
    }

    @GetMapping("/rfp/{rfpId}/best")
    public Proposal getBestProposal(@PathVariable Long rfpId) {
        return proposalService.getBestProposal(rfpId);
    }

    @GetMapping("/rfp/{rfpId}/best/formatted")
    public BestProposalResponse getBestProposalFormatted(@PathVariable Long rfpId) {
        return proposalService.getBestProposalFormatted(rfpId);
    }

    @GetMapping("/rfp/{rfpId}/compare")
    public List<ProposalComparisonDTO> compareProposals(@PathVariable Long rfpId) {
        return proposalService.compareProposals(rfpId);
    }
}
