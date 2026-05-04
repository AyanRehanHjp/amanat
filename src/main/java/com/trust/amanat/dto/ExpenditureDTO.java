package com.trust.amanat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ExpenditureDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expDate;
    private String receiptNo;
    private String name;
    private String address;
    private Double amount;
    private int year;
    private String problem;
    private String supDoc;
}
