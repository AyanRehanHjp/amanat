package com.trust.amanat.service;

import com.trust.amanat.dto.ScanPayDTO;
import com.trust.amanat.entity.ScanPayEntity;

import java.util.List;

public interface ScanPayService {
    ScanPayEntity addPayee(ScanPayDTO scanPayDTO);
    public List<ScanPayEntity> getAllPayments();
    public String updateStatus(Long id, String status) ;
    List<ScanPayEntity> getPaymentsByStatus(String status);
    }
