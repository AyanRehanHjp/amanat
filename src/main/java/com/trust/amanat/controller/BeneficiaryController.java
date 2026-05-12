package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.BeneficiaryDTO;
import com.trust.amanat.entity.BeneficiaryEntity;
import com.trust.amanat.service.BeneficiaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/beneficiary")
public class BeneficiaryController {
    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);
    @Autowired
    BeneficiaryService beneficiaryService;

    @PostMapping("/addBeneficiary")
    public ResponseEntity<?> addBeneficiary(
            @RequestPart("beneficiary") BeneficiaryDTO beneficiaryDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        logger.info("Received addBeneficiary request for needyName: {}", beneficiaryDTO.getNeedyName());
        if(file != null && !file.isEmpty()){
            logger.info("File received: {}", file.getOriginalFilename());
        } else {
            logger.info("No file uploaded in request");
        }
        BeneficiaryEntity submittedDetails = beneficiaryService.addBeneficiary(beneficiaryDTO, file);
        if (submittedDetails != null) {
            logger.info("Beneficiary saved successfully with ID: {}", submittedDetails.getId());
            return new ResponseEntity<>(AppConstants.Message.SUCCESSFUL_ASKING_REQUEST + submittedDetails.getTokenId() + AppConstants.Message.WHATSAPP_AND_EMAIL_CONTACT_MSG+AppConstants.Message.WHATSAPP_AND_GMAIL, HttpStatus.CREATED);
        } else {
            logger.error("Failed to save beneficiary");
            return new ResponseEntity<>(AppConstants.Message.BENEFICIARY_FAILED, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allBeneficiaries")
    public ResponseEntity<?> getAllBeneficiaries() {

        List<BeneficiaryEntity> list = beneficiaryService.getAllBeneficiaries();
        logger.info("Retrieved {} beneficiaries", list.size());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,@RequestBody Map<String, Object> data) {

        String status = (String) data.get("status");
        Integer amount = null;

        if (data.get("amount") != null && !data.get("amount").toString().trim().isEmpty()) {
            logger.info("Updating beneficiary with ID: {}, Status: {}, Amount: {}", id, status, data.get("amount"));
            amount = Integer.parseInt(data.get("amount").toString());
            logger.info("Parsed amount: {}", amount);
        }
        BeneficiaryEntity updated = beneficiaryService.updateStatus(id, status, amount);

        if(updated != null){
            logger.info("Beneficiary with ID: {} updated successfully", id);
            return ResponseEntity.ok(AppConstants.Message.BENEFICIARY_UPDATED);
        }else{
            logger.warn("Failed to update beneficiary with ID: {}", id);
            return ResponseEntity.badRequest().body(AppConstants.Message.BENEFICIARY_UPDATE_FAILED);
        }
    }
    @GetMapping("/track/{tokenId}")
    public ResponseEntity<?> trackByToken(@PathVariable String tokenId) {

        BeneficiaryEntity data = beneficiaryService.findByToken(tokenId);

        if (data != null) {
            logger.info("Beneficiary found for tokenId: {}", tokenId);
            return ResponseEntity.ok(data);
        } else {
            logger.error("Invalid tokenId: {}", tokenId);
            return ResponseEntity.badRequest().body("Invalid Token");
        }
    }
}
