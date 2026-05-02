package com.trust.amanat.service;

import com.trust.amanat.dto.BeneficiaryDTO;
import com.trust.amanat.entity.BeneficiaryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BeneficiaryService {
    public BeneficiaryEntity addBeneficiary(BeneficiaryDTO beneficiaryDTO , MultipartFile file) ;
    public List<BeneficiaryEntity> getAllBeneficiaries() ;
    public BeneficiaryEntity updateStatus(Long id, String status, Integer amount);
}
