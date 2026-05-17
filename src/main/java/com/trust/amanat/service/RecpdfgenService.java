package com.trust.amanat.service;

import com.trust.amanat.dto.RecPdfGenDTO;
import com.trust.amanat.entity.RecPdfGenEntity;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface RecpdfgenService {
    public void generatePdf(String receiptNo,
                            HttpServletResponse response) throws Exception ;
    public boolean checkReceipt(String receiptNo);
    public RecPdfGenEntity saveReceipt(RecPdfGenDTO dto);
    String saveReceiptImage(Map<String,String> data);
    }
