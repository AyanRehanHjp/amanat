package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table (name = "income_details")
public class IncomeDetEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "member_id")
    private String memberId;
    private Double amount;
    private String forMonth;
    private int forYear;
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    private String comment;
}
