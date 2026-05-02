package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "BENEFICIARY_DETAILS")
public class BeneficiaryEntity {
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String needyName;
    private String mobile;
    private String address;
    private String pinCode;
    private String state;

    private String problem;
    private String financialCondition;
    private String familyOccupation;
    private String comment;
    private String status;
    private Integer amount;
    private String documentPath;
    @Column(name = "supportive_documents")
    private String supportiveDocuments;

}
