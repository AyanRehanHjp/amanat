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
            return new ResponseEntity<>(AppConstants.Message.BENEFICIARY_CREATED, HttpStatus.CREATED);
        } else {
            logger.error("Failed to save beneficiary");
            return new ResponseEntity<>(AppConstants.Message.BENEFICIARY_FAILED, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allBeneficiaries")
    public ResponseEntity<?> getAllBeneficiaries() {

        List<BeneficiaryEntity> list = beneficiaryService.getAllBeneficiaries();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,@RequestBody Map<String, Object> data) {

        String status = (String) data.get("status");
        Integer amount = data.get("amount") != null ? Integer.parseInt(data.get("amount").toString()): 0;

        BeneficiaryEntity updated = beneficiaryService.updateStatus(id, status, amount);

        if(updated != null){
            return ResponseEntity.ok(AppConstants.Message.BENEFICIARY_UPDATED);
        }else{
            return ResponseEntity.badRequest().body(AppConstants.Message.BENEFICIARY_UPDATE_FAILED);
        }
    }
}
