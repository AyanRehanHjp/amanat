package com.trust.amanat.serviceImpl;

import com.trust.amanat.dto.BeneficiaryDTO;
import com.trust.amanat.entity.BeneficiaryEntity;
import com.trust.amanat.repository.BeneficiaryRepository;
import com.trust.amanat.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
    @Autowired
    BeneficiaryRepository beneficiaryRepository;

    @Override
    public BeneficiaryEntity addBeneficiary(BeneficiaryDTO beneficiaryDTO) {

        BeneficiaryEntity beneficiary = new BeneficiaryEntity();

        beneficiary.setNeedyName(beneficiaryDTO.getNeedyName());
        beneficiary.setMobile(beneficiaryDTO.getMobile());
        beneficiary.setAddress(beneficiaryDTO.getAddress());
        beneficiary.setPinCode(beneficiaryDTO.getPinCode());
        beneficiary.setState(beneficiaryDTO.getState());
        beneficiary.setProblem(beneficiaryDTO.getProblem());
        beneficiary.setFamilyOccupation(beneficiaryDTO.getFamilyOccupation());
        beneficiary.setFinancialCondition(beneficiaryDTO.getFinancialCondition());
        beneficiary.setComment(beneficiaryDTO.getComment());
        return beneficiaryRepository.save(beneficiary);
    }

    @Override
    public List<BeneficiaryEntity> getAllBeneficiaries() {
        return beneficiaryRepository.findAll();
    }

    @Override
    public BeneficiaryEntity updateStatus(Long id, String status, Integer amount){

        BeneficiaryEntity beneficiary = beneficiaryRepository.findById(id).orElse(null);

        if(beneficiary == null){
            return null;
        }

        beneficiary.setStatus(status);
        beneficiary.setAmount(amount);

        return beneficiaryRepository.save(beneficiary);
    }
}
