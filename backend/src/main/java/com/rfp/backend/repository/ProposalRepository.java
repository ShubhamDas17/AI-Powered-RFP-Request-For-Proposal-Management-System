package com.rfp.backend.repository;

import com.rfp.backend.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    // Fetch all proposals for an RFP
    List<Proposal> findByRfpId(Long rfpId);

    // Fetch all proposals for a vendor
    List<Proposal> findByVendorId(Long vendorId);

    // Delete proposals for vendor
    void deleteByVendorId(Long vendorId);
}
