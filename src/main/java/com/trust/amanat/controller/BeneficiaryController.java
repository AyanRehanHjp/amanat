package com.trust.amanat.controller;

import com.trust.amanat.dto.BeneficiaryDTO;
import com.trust.amanat.entity.BeneficiaryEntity;
import com.trust.amanat.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/beneficiary")
public class BeneficiaryController {

    @Autowired
    BeneficiaryService beneficiaryService;

    @PostMapping("/addBeneficiary")
    public ResponseEntity <?> addBeneficiary(@RequestBody BeneficiaryDTO beneficiaryDTO) {
        BeneficiaryEntity submittedDetails = beneficiaryService.addBeneficiary(beneficiaryDTO);
        if (submittedDetails != null) {
            return new ResponseEntity<>("Your details submitted successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>( "Something went wrong, Please try again",HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping("/allBeneficiaries")
    public ResponseEntity<?> getAllBeneficiaries() {

        List<BeneficiaryEntity> list = beneficiaryService.getAllBeneficiaries();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Object> data) {

        String status = (String) data.get("status");
        Integer amount = data.get("amount") != null
                ? Integer.parseInt(data.get("amount").toString())
                : 0;

        BeneficiaryEntity updated = beneficiaryService.updateStatus(id, status, amount);

        if(updated != null){
            return ResponseEntity.ok("Updated Successfully");
        }else{
            return ResponseEntity.badRequest().body("Update Failed");
        }
    }
}
