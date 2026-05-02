package com.trust.amanat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryDTO {

    private String needyName;
    private String mobile;
    private String address;
    private String pinCode;
    private String state;

    private String problem;
    private String financialCondition;
    private String familyOccupation;
    private String comment;
    private String supportiveDocuments;
}
