package com.rfp.backend.controller;

import com.rfp.backend.model.Vendor;
import com.rfp.backend.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
@CrossOrigin
public class VendorController {

    private final VendorService vendorService;

    @GetMapping("/list")
    public List<Vendor> list() {
        return vendorService.getAllVendors(); // uses service
    }

    @PostMapping("/add")
    public Vendor addVendor(@RequestBody Vendor vendor) {
        return vendorService.addVendor(vendor.getName(), vendor.getEmail());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorForce(id);
        return "Vendor deleted successfully (with proposals)";
    }
}
