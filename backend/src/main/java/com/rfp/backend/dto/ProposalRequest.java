package com.rfp.backend.dto;

import lombok.Data;

@Data
public class ProposalRequest {
    private Long vendorId;
    private Long rfpId;
    private String proposalText;
}
