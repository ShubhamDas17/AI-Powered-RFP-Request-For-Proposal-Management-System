package com.rfp.backend.service;

import com.rfp.backend.model.RFP;
import com.rfp.backend.repository.RFPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RFPService {

    private final RFPRepository rfpRepository;

    public RFP createRFP(String originalText, String structuredJson) {
        RFP rfp = new RFP();
        rfp.setOriginalDescription(originalText);
        rfp.setStructuredData(structuredJson);
        rfp.setCreatedAt(LocalDateTime.now());
        return rfpRepository.save(rfp);
    }

    public RFP getRFP(Long id) {
        return rfpRepository.findById(id).orElse(null);
    }

    public List<RFP> getAllRFPs() {
        return rfpRepository.findAll();
    }
}
