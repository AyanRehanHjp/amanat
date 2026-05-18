package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table (name = "expenditures_details")
public class ExpenditureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "exp_date")
        private Date expDate;
        @Column(name = "receipt_no")
        private String receiptNo;
        private String name;
        private String address;
        private Double amount;
        private int year;
        private String problem;
        private String supDoc;
    @Column(name = "receipt_image_url")
    private String receiptImageUrl;

    @Column(name = "receipt_generated")
    private String receiptGenerated;

    @Column(name = "beneficiary_id")
    private Long beneficiaryId;
}
