package com.rfp.backend.controller;

import com.rfp.backend.dto.CreateRFPRequest;
import com.rfp.backend.model.RFP;
import com.rfp.backend.model.Vendor;
import com.rfp.backend.service.AiService;
import com.rfp.backend.service.EmailService;
import com.rfp.backend.service.RFPService;
import com.rfp.backend.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rfp")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RFPController {

    private final AiService aiService;
    private final RFPService rfpService;
    private final VendorService vendorService;
    private final EmailService emailService;

    @PostMapping("/create")
    public RFP createRFP(@RequestBody CreateRFPRequest request) {
        String structuredJson = aiService.extractStructuredDataFromText(request.getDescription());
        return rfpService.createRFP(request.getDescription(), structuredJson);
    }

    @GetMapping("/{id}")
    public RFP getRFP(@PathVariable Long id) {
        return rfpService.getRFP(id);
    }

    @GetMapping
    public List<RFP> getAllRFPs() {
        return rfpService.getAllRFPs();
    }

    @PostMapping("/send/{id}")
    public String sendEmailToVendors(@PathVariable Long id) {
        RFP rfp = rfpService.getRFP(id);
        List<Vendor> vendors = vendorService.getAllVendors();
        for (Vendor vendor : vendors) {
            emailService.sendEmail(vendor.getEmail(), "Request For Proposal", rfp.getOriginalDescription());
        }
        return "Emails sent (attempted)";
    }
}
