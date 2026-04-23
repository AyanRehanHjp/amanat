package com.trust.amanat.service;

import com.trust.amanat.dto.BeneficiaryDTO;
import com.trust.amanat.entity.BeneficiaryEntity;

import java.util.List;

public interface BeneficiaryService {
    public BeneficiaryEntity addBeneficiary(BeneficiaryDTO beneficiaryDTO);
    public List<BeneficiaryEntity> getAllBeneficiaries() ;
    public BeneficiaryEntity updateStatus(Long id, String status, Integer amount);
}
