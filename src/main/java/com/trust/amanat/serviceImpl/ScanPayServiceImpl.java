package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.ScanPayDTO;
import com.trust.amanat.entity.ScanPayEntity;
import com.trust.amanat.repository.ScanPayRepository;
import com.trust.amanat.service.ScanPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScanPayServiceImpl implements ScanPayService {
    private static final Logger logger = LoggerFactory.getLogger(ScanPayServiceImpl.class);
    @Autowired
    ScanPayRepository scanPayRepository;

    @Override
    @Transactional
    public ScanPayEntity addPayee(ScanPayDTO scanPayDTO){
        try {
            logger.info("addPayee() called with DTO: {}", scanPayDTO);
            ScanPayEntity scanPayEntity = new ScanPayEntity();
            scanPayEntity.setPayeeName(scanPayDTO.getPayeeName());
            scanPayEntity.setMemberId(scanPayDTO.getMemberId());
            scanPayEntity.setMobile(scanPayDTO.getMobile());
            scanPayEntity.setAmount(scanPayDTO.getAmount());
            scanPayEntity.setPayDate(scanPayDTO.getPayDate());
            scanPayEntity.setUtrNo(scanPayDTO.getUtrNo());
            scanPayEntity.setComment(scanPayDTO.getComment());
            scanPayEntity.setEntryStatus(AppConstants.Message.PENDING);
            ScanPayEntity payeeSaved = scanPayRepository.save(scanPayEntity);
            logger.info("pay details Saved");
            return payeeSaved;
        } catch (Exception e) {
            logger.error("Error in addPayee: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ScanPayEntity> getAllPayments() {
        logger.info("getAllPayments() called");

        List <ScanPayEntity> allPayList=  scanPayRepository.findAll();
        if(allPayList.isEmpty()){
            logger.info("No payments found in the database.");
            return null;
        } else {
            logger.info("Payments retrieved: {}", allPayList.size());
            return allPayList;

        }
    }

        @Override
        public String updateStatus(Long id, String status) {

            ScanPayEntity entity = scanPayRepository.findById(id).orElseThrow(() ->
             new RuntimeException("Record not found"));
            // Done hone ke baad wapas Pending nahi
            if ("DONE".equalsIgnoreCase(entity.getEntryStatus())) {
                logger.info("Attempt to update status for id={} but it's already DONE", id);
                return "Status already DONE";
            }
            if(!"DONE".equalsIgnoreCase(status)){
                logger.info("Invalid status update attempt for id={} with status={}", id, status);
                return "Invalid Status";
            }
            entity.setEntryStatus(status);
            scanPayRepository.save(entity);
            logger.info("Status updated to {} for id={}", status, id);
            return "Status updated successfully";
        }


    @Override
    public List<ScanPayEntity> getPaymentsByStatus(String status) {

        logger.info("getPaymentsByStatus() called with status={}", status);

        List<ScanPayEntity> paymentList = scanPayRepository.findByStatus(status);
        if(paymentList.isEmpty()){
            logger.info("No records found for status={}", status);
            return null;
        }
        logger.info("Found {} records for status={}", paymentList.size(),status);
        return paymentList;
    }

}
