package com.rfp.backend.repository;

import com.rfp.backend.model.RFP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RFPRepository extends JpaRepository<RFP, Long> {
}
