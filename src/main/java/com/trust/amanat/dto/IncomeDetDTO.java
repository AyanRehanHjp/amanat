package com.trust.amanat.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeDetDTO {
    @Column(name = "member_id")
    private String memberId;
    private Double amount;
    private String forMonth;
    private Integer forYear;
    private String paymentDate;
    private String comment;
    private String inc_receipt_no;


}
