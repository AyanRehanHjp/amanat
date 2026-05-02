package com.trust.amanat.serviceImpl;

import com.trust.amanat.controller.BeneficiaryController;
import com.trust.amanat.dto.BeneficiaryDTO;
import com.trust.amanat.entity.BeneficiaryEntity;
import com.trust.amanat.repository.BeneficiaryRepository;
import com.trust.amanat.service.BeneficiaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);
    @Autowired
    BeneficiaryRepository beneficiaryRepository;

    @Override
    public BeneficiaryEntity addBeneficiary(BeneficiaryDTO beneficiaryDTO , MultipartFile file) {
        logger.info("Saving beneficiary: {}", beneficiaryDTO.getNeedyName());

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

        if(file != null && !file.isEmpty()){
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/helprequests/" + fileName);
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                beneficiary.setSupportiveDocuments(fileName);
                logger.info("File saved successfully at: {}", path.toAbsolutePath());
            } catch (IOException e) {
                logger.error("Error saving file: {}", e.getMessage());
                throw new RuntimeException("File saving failed", e);
            }
        } else {
            logger.info("No file uploaded for beneficiary: {}", beneficiaryDTO.getNeedyName());
        }

        BeneficiaryEntity saved = beneficiaryRepository.save(beneficiary);
        logger.info("Beneficiary saved with ID: {}", saved.getId());
        return saved;
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
