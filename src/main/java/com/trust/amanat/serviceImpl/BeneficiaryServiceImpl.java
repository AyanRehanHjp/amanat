package com.trust.amanat.serviceImpl;

import com.trust.amanat.controller.BeneficiaryController;
import com.trust.amanat.dto.BeneficiaryDTO;
import com.trust.amanat.entity.BeneficiaryEntity;
import com.trust.amanat.entity.ExpenditureEntity;
import com.trust.amanat.repository.BeneficiaryRepository;
import com.trust.amanat.repository.ExpenditureRepository;
import com.trust.amanat.service.BeneficiaryService;
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
    public BeneficiaryEntity updateStatus(Long id, String status, Integer amount){

        BeneficiaryEntity beneficiary = beneficiaryRepository.findById(id).orElse(null);

        if(beneficiary == null){
            return null;
        }

        if ("ACCEPTED".equals(status)) {

            if (amount == null || amount == 0){
                return null;
            }

            beneficiary.setAmount(amount);
            ExpenditureEntity lastExp =
                    expenditureRepository
                            .findTopByOrderByIdDesc();

            String nextReceiptNo = "REC-1";

            if(lastExp != null &&
                    lastExp.getReceiptNo() != null){

                String lastNo =
                        lastExp.getReceiptNo()
                                .replace("REC-", "");

                int num = Integer.parseInt(lastNo);

                nextReceiptNo = "REC-" + (num + 1);
            }

            ExpenditureEntity exp =
                    new ExpenditureEntity();

            exp.setName(
                    beneficiary.getNeedyName()
            );

            exp.setAddress(
                    beneficiary.getAddress()
            );

            exp.setAmount(
                    Double.valueOf(amount)
            );

            exp.setProblem(
                    beneficiary.getProblem()
            );

            exp.setYear(
                    new Date().getYear() + 1900
            );

            exp.setReceiptNo(
                    nextReceiptNo
            );

            exp.setExpDate(
                    new Date()
            );

            exp.setSupDoc(
                    beneficiary.getSupportiveDocuments()
            );

            exp.setReceiptGenerated("N");

            exp.setBeneficiaryId(
                    beneficiary.getId()
            );

            expenditureRepository.save(exp);
        }
        else if ("REJECTED".equals(status)) {
            beneficiary.setAmount(0);
        }
        else if ("PENDING".equals(status) || "WORKING".equals(status)) {
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
