package com.trust.amanat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScanPayDTO {
    private Long id;

    private String payeeName;
    private String memberId;
    private String utrNo;
    private double amount;
    private String mobile;
    private String payDate;
}
