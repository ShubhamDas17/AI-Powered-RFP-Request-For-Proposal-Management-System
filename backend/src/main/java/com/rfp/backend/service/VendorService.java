package com.rfp.backend.service;

import com.rfp.backend.model.Vendor;
import com.rfp.backend.repository.ProposalRepository;
import com.rfp.backend.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final ProposalRepository proposalRepository;

    public Vendor addVendor(String name, String email) {
        Vendor v = new Vendor();
        v.setName(name);
        v.setEmail(email);
        return vendorRepository.save(v);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public void deleteVendorForce(Long vendorId) {

        // 1️⃣ Delete all proposals linked with vendor
        proposalRepository.deleteByVendorId(vendorId);

        // 2️⃣ Now delete vendor
        vendorRepository.deleteById(vendorId);
    }

//    public void deleteVendorSafe(Long vendorId) {
//        if (!vendorRepository.existsById(vendorId)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found");
//        }
//
//        long proposals = proposalRepository.countByVendorId(vendorId);
//        if (proposals > 0) {
//            throw new ResponseStatusException(
//                    HttpStatus.CONFLICT,
//                    "Cannot delete vendor: vendor has " + proposals + " proposal(s)"
//            );
//        }
//        vendorRepository.deleteById(vendorId);
//    }
}

