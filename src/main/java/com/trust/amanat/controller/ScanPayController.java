package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.ScanPayDTO;
import com.trust.amanat.entity.ScanPayEntity;
import com.trust.amanat.service.ScanPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/scan&pay")
public class ScanPayController {

    private static final Logger logger = LoggerFactory.getLogger(ScanPayController.class);

    @Autowired
    ScanPayService scanPayService;

    @PostMapping("/addPayee")
    public ResponseEntity<String> addPayee(@RequestBody ScanPayDTO scanPayDTO) {
         logger.info("addPayee called for scanPayDTO mobile={}", scanPayDTO != null ? scanPayDTO.getMobile() : null);
        ScanPayEntity payEntity = scanPayService.addPayee(scanPayDTO);
         if(payEntity!= null){
             logger.info("addPayee completed for mobile={}", scanPayDTO != null ? scanPayDTO.getMobile() : null);
             return new ResponseEntity<>(AppConstants.Message.PAYMENT_DETAILS_SUBMITTED, HttpStatus.CREATED) ;

         }
         else {
        logger.error("addPayee failed for mobile={}", scanPayDTO != null ? scanPayDTO.getMobile() : null);
        return new ResponseEntity<>(AppConstants.Message.PAYMENT_FAILED, HttpStatus.BAD_REQUEST) ;
    }}



    @GetMapping("/allPayments")
    public List<ScanPayEntity> getAllPayments() {

        return scanPayService.getAllPayments();
    }

}
