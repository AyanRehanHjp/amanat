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
import java.util.Map;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/recpdfgen")
public class RecpdfgenController {

    @Autowired
    private RecPdfGenRepository receiptRepository;

    @Autowired
    private ExpenditureRepository expenditureRepository;


    @PostMapping("/generateReceipt")
    public ResponseEntity<?> generateReceipt(@RequestBody RecPdfGenDTO dto){

        Optional<RecPdfGenEntity> receipt =
                receiptRepository.findByReceiptNo(dto.getReceiptNo());

        if(receipt.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(AppConstants.Message.RECEIPT_ALREADY_GENERATED);
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
        }
        return ResponseEntity.ok(rec);
    }



    @GetMapping("/downloadReceipt/{receiptNo}")
    public ResponseEntity<?> getReceipt(@PathVariable String receiptNo){

        RecPdfGenEntity rec =
                receiptRepository.findByReceiptNo(receiptNo).orElse(null);
        if(rec != null){
            return ResponseEntity.ok(rec);

        }
        return ResponseEntity.status(404).body(AppConstants.Message.RECEIPT_NOT_FOUND);


    }
    @PostMapping("/savePdf")
    public ResponseEntity<?> savePdf(@RequestBody Map<String,String> data){

        try{

            String pdfBase64 = data.get("pdf");
            String receiptNo = data.get("receiptNo");
            byte[] pdfBytes = Base64.getDecoder().decode(pdfBase64);
            Path path = Paths.get("uploads/receipts/" + receiptNo + ".pdf");
            Files.createDirectories(path.getParent());
            Files.write(path, pdfBytes);
            return ResponseEntity.ok("PDF Saved");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("PDF Save Failed");
        }
    }
}