package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "receipt_details")
public class RecPdfGenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "receipt_no", unique = true)
    private String receiptNo;
    private String name;
    private String address;
    private double amount;
    private String recDate;
}
