package com.rfp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    @OnDelete(action = OnDeleteAction.CASCADE)   // <-- Add this
    private Vendor vendor;


    @ManyToOne
    private RFP rfp;

    // user-written proposal text
    @Column(columnDefinition = "TEXT")
    private String proposalText;

    // AI generated structured JSON or additional formatting
    @Column(columnDefinition = "TEXT")
    private String structuredResponse;

    // AI score (0–100)
    private Integer score;
}
