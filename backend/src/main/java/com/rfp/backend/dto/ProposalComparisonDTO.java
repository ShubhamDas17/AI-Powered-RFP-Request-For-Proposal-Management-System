package com.rfp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProposalComparisonDTO {
    private String vendor;
    private Integer score;
    private String reason;
}
