package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.RecPdfGenDTO;
import com.trust.amanat.entity.ExpenditureEntity;
import com.trust.amanat.entity.RecPdfGenEntity;
import com.trust.amanat.repository.ExpenditureRepository;
import com.trust.amanat.repository.RecPdfGenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/recpdfgen")
public class RecpdfgenController {

    private static final Logger logger = LoggerFactory.getLogger(RecpdfgenController.class);

    @Autowired
    private RecPdfGenRepository receiptRepository;

    @Autowired
    private ExpenditureRepository expenditureRepository;


    @PostMapping("/generateReceipt")
    public ResponseEntity<?> generateReceipt(@RequestBody RecPdfGenDTO dto){

        logger.info("generateReceipt called for receiptNo={}", dto != null ? dto.getReceiptNo() : null);

        Optional<RecPdfGenEntity> receipt =receiptRepository.findByReceiptNo(dto.getReceiptNo());
        logger.info("Checking if receipt already exists for receiptNo={}: {}", dto.getReceiptNo(), receipt.isPresent());
        if(receipt.isPresent()){
            logger.warn("Receipt already exists for receiptNo={}", dto.getReceiptNo());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(AppConstants.Message.RECEIPT_ALREADY_GENERATED);
        }

        RecPdfGenEntity rec = new RecPdfGenEntity();

        rec.setReceiptNo(dto.getReceiptNo());
        rec.setName(dto.getName());
        rec.setAddress(dto.getAddress());
        rec.setAmount(dto.getAmount());
        rec.setRecDate(dto.getRecDate());

        receiptRepository.save(rec);
        ExpenditureEntity exp = expenditureRepository.findByReceiptNo(dto.getReceiptNo());

        if(exp != null){
            exp.setReceiptGenerated("Y");
            expenditureRepository.save(exp);
            logger.info("Expenditure with receiptNo={} marked as receipt generated", dto.getReceiptNo());
        }
        logger.info("Receipt generated successfully for receiptNo={}", dto.getReceiptNo());
        return ResponseEntity.ok(rec);
    }



    @GetMapping("/downloadReceipt/{receiptNo}")
    public ResponseEntity<?> getReceipt(@PathVariable String receiptNo){
        logger.info("downloadReceipt called for receiptNo={}", receiptNo);
        RecPdfGenEntity rec =
                receiptRepository.findByReceiptNo(receiptNo).orElse(null);
        if(rec != null){
            logger.info("Receipt found for receiptNo={}", receiptNo);
            return ResponseEntity.ok(rec);

        }
        logger.warn("Receipt not found for receiptNo={}", receiptNo);
        return ResponseEntity.status(404).body(AppConstants.Message.RECEIPT_NOT_FOUND);


    }
    @PostMapping("/savePdf")
    public ResponseEntity<?> savePdf(@RequestBody Map<String,String> data){

        try{

            logger.info("savePdf called for receiptNo={}", data != null ? data.get("receiptNo") : null);

            String pdfBase64 = data.get("pdf");
            String receiptNo = data.get("receiptNo");
            byte[] pdfBytes = Base64.getDecoder().decode(pdfBase64);
            Path path = Paths.get("uploads/receipts/" + receiptNo + ".pdf");
            Files.createDirectories(path.getParent());
            Files.write(path, pdfBytes);
            logger.info("PDF saved successfully for receiptNo={}", receiptNo);
            return ResponseEntity.ok("PDF Saved");
        }catch(Exception e){
            logger.error("Error saving PDF for receiptNo={}: {}", data != null ? data.get("receiptNo") : null, e.getMessage());
            return ResponseEntity.badRequest().body("PDF Save Failed");
        }
    }
}