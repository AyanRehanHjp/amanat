package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table (name = "income_details")
public class IncomeDetEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "member_id",unique = true)
    private String memberId;
    private Double amount;
    private String month;
    private int year;
}
