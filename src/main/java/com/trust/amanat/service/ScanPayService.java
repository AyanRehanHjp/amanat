package com.trust.amanat.service;

import com.trust.amanat.dto.ScanPayDTO;
import com.trust.amanat.entity.ScanPayEntity;

public interface ScanPayService {
    public ScanPayEntity addPayee(ScanPayDTO scanPayDTO);
}
