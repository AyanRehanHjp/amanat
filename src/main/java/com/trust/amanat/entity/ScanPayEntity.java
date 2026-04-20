package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SCAN_PAY_DETAILS")
public class ScanPayEntity {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    private String payeeName;
    private String memberId;
    private String utrNo;
    private double amount;
    private String mobile;
    private String payDate;
}
