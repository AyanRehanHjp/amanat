package com.trust.amanat.serviceImpl;

import com.trust.amanat.dto.ScanPayDTO;
import com.trust.amanat.entity.ScanPayEntity;
import com.trust.amanat.repository.ScanPayRepository;
import com.trust.amanat.service.ScanPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScanPayServiceImpl implements ScanPayService {
    @Autowired
    ScanPayRepository scanPayRepository;

    @Override
    public ScanPayEntity addPayee(ScanPayDTO scanPayDTO){
        ScanPayEntity scanPayEntity = new ScanPayEntity();
        scanPayEntity.setPayeeName(scanPayDTO.getPayeeName());
        scanPayEntity.setMemberId(scanPayDTO.getMemberId());
        scanPayEntity.setMobile(scanPayDTO.getMobile());
        scanPayEntity.setAmount(scanPayDTO.getAmount());
        scanPayEntity.setPayDate(scanPayDTO.getPayDate());
        scanPayEntity.setUtrNo(scanPayDTO.getUtrNo());
        return scanPayRepository.save(scanPayEntity);

    }


}
