package com.trust.amanat.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecPdfGenDTO {
    private String receiptNo;
    private String name;
    private String address;
    private double amount;
    private String recDate;
}
