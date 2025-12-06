package com.rfp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class RFP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String originalDescription;

    @Column(columnDefinition="TEXT")
    private String structuredData;

    private LocalDateTime createdAt;
}
