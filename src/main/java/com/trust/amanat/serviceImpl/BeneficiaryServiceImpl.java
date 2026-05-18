package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.controller.BeneficiaryController;
import com.trust.amanat.dto.BeneficiaryDTO;
import com.trust.amanat.entity.BeneficiaryEntity;
import com.trust.amanat.entity.ExpenditureEntity;
import com.trust.amanat.repository.BeneficiaryRepository;
import com.trust.amanat.repository.ExpenditureRepository;
import com.trust.amanat.service.BeneficiaryService;
import com.trust.amanat.service.CloudinaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {
    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryServiceImpl.class);
    @Autowired
    BeneficiaryRepository beneficiaryRepository;

    @Autowired
    ExpenditureRepository expenditureRepository;
    @Autowired
    CloudinaryService cloudinaryService;


    @Override
    @Transactional
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
        beneficiary.setStatus(AppConstants.Message.PENDING);

        if(file != null && !file.isEmpty()){
            try {
                String fileUrl =
                        cloudinaryService.uploadFile(file, AppConstants.Message.BENEFICIARY_DOCUMENTS);
                beneficiary.setSupportiveDocuments(fileUrl);
                logger.info("File uploaded to Cloudinary successfully");
            } catch (Exception e) {
                logger.error("Cloudinary upload failed: {}", e.getMessage());
                throw new RuntimeException("File upload failed", e);
            }
        } else {
            logger.info("No file uploaded for beneficiary: {}", beneficiaryDTO.getNeedyName());
        }
// Generate token: AWT + 5 random digits
        String token = "AWT" + String.format("%05d", (int)(Math.random() * 100000));
        beneficiary.setTokenId(token);
        BeneficiaryEntity saved = beneficiaryRepository.save(beneficiary);
        logger.info("Beneficiary saved with ID: {}", saved.getId());
        return saved;
    }
    @Override
    public List<BeneficiaryEntity> getAllBeneficiaries() {

        return beneficiaryRepository.findAll();
    }

    @Override
    @Transactional
    public BeneficiaryEntity updateStatus(Long id, String status, Integer amount) {

        BeneficiaryEntity beneficiary = beneficiaryRepository.findById(id).orElse(null);
        if (beneficiary == null) {
            return null;
        }
        if (AppConstants.Message.ACCEPTED.equals(status)) {
            if (amount == null || amount == 0) {
                return null;
            }
            beneficiary.setAmount(amount);
            ExpenditureEntity lastExp = expenditureRepository.findTopByOrderByIdDesc();
            String nextReceiptNo = "001";
            if (lastExp != null && lastExp.getReceiptNo() != null) {
                int num = Integer.parseInt(lastExp.getReceiptNo());
                nextReceiptNo = String.format("%03d",(num + 1));
            }
            ExpenditureEntity exp = new ExpenditureEntity();
            exp.setName(beneficiary.getNeedyName());
            exp.setAddress(beneficiary.getAddress());
            exp.setAmount(Double.valueOf(amount));
            exp.setProblem(beneficiary.getProblem());
            exp.setYear(new Date().getYear() + 1900);
            exp.setReceiptNo(nextReceiptNo);
            exp.setExpDate(new Date());
            exp.setSupDoc(beneficiary.getSupportiveDocuments());
            exp.setReceiptGenerated("N");
            exp.setBeneficiaryId(beneficiary.getId());
            expenditureRepository.save(exp);

        } else if (AppConstants.Message.REJECTED.equals(status)) {
            beneficiary.setAmount(0);
        } else if (AppConstants.Message.PENDING.equals(status) || AppConstants.Message.WORKING.equals(status)) {
            beneficiary.setAmount(null);
        }
        beneficiary.setStatus(status);

        return beneficiaryRepository.save(beneficiary);
    }

    @Override
    public BeneficiaryEntity findByToken(String tokenId) {

        return beneficiaryRepository.findByTokenId(tokenId);
    }
}
