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
@RequestMapping("/scan-pay")
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
    logger.info("getAllPayments called");
        return scanPayService.getAllPayments();
    }


    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id,@RequestParam String status) {
        logger.info("updateStatus called for id={} status={}", id, status);
        try {

            String response = scanPayService.updateStatus(id, status);
            logger.info("Status updated successfully for id={}", id);
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            logger.error("Error while updating status for id={} : {}", id, e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AppConstants.Message.SOMETHING_WRONG);
        }
    }
    @GetMapping("/paymentsByStatus")
    public ResponseEntity<List<ScanPayEntity>> getPaymentsByStatus(  @RequestParam String status) {

        logger.info("getPaymentsByStatus called with status={}", status);
        List<ScanPayEntity> paymentList =scanPayService.getPaymentsByStatus(status);
        logger.info("Payments retrieved for status={} : count={}", status, paymentList != null ? paymentList.size() : 0);
        return ResponseEntity.ok(paymentList);
    }
}
