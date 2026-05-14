package com.trust.amanat.serviceImpl;

import com.trust.amanat.dto.ScanPayDTO;
import com.trust.amanat.entity.ScanPayEntity;
import com.trust.amanat.repository.ScanPayRepository;
import com.trust.amanat.service.ScanPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            ScanPayEntity payeeSaved = scanPayRepository.save(scanPayEntity);
            logger.info("pay details Saved");
            return payeeSaved;
        } catch (Exception e) {
            logger.error("Error in addPayee: {}", e.getMessage(), e);
            throw e;
        }
    }


}
