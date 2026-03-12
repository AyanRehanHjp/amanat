package com.trust.amanat.controller;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import com.trust.amanat.dto.RecPdfGenDTO;
import com.trust.amanat.entity.RecPdfGenEntity;
import com.trust.amanat.repository.RecPdfGenRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/recpdfgen")
public class RecpdfgenController {

    @Autowired
    private RecPdfGenRepository receiptRepository;


    @PostMapping("/generateReceipt")
    public ResponseEntity<?> generateReceipt(@RequestBody RecPdfGenDTO dto){

        Optional<RecPdfGenEntity> receipt =
                receiptRepository.findByReceiptNo(dto.getReceiptNo());

        if(receipt.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Receipt already generated");
        }

        RecPdfGenEntity rec = new RecPdfGenEntity();

        rec.setReceiptNo(dto.getReceiptNo());
        rec.setName(dto.getName());
        rec.setAddress(dto.getAddress());
        rec.setAmount(dto.getAmount());
        rec.setRecDate(dto.getRecDate());

        receiptRepository.save(rec);

        return ResponseEntity.ok(rec);
    }



    @GetMapping("/downloadReceipt/{receiptNo}")
    public void downloadReceipt(@PathVariable String receiptNo,
                                HttpServletResponse response) throws Exception {

        RecPdfGenEntity rec =
                receiptRepository.findByReceiptNo(receiptNo).orElse(null);

        if(rec == null){
            response.sendError(404,"Receipt not found");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=receipt_"+receiptNo+".pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);


        /* ===== LOGO ===== */

        ImageData logoData =
                ImageDataFactory.create(
                        getClass().getClassLoader().getResource("static/images/logo.png")
                );

        Image logo = new Image(logoData).scaleToFit(80,80);

        document.add(logo);


        /* ===== TITLE ===== */

        Paragraph title =
                new Paragraph("AMANAT WELFARE TRUST")
                        .setBold()
                        .setFontSize(20);

        document.add(title);

        Paragraph subtitle =
                new Paragraph("Donation Receipt");

        document.add(subtitle);


        document.add(new Paragraph(" "));


        /* ===== RECEIPT DETAILS ===== */

        Table table = new Table(2);

        table.addCell("Receipt Number");
        table.addCell(rec.getReceiptNo());

        table.addCell("Date");
        table.addCell(rec.getRecDate());

        table.addCell("Donor Name");
        table.addCell(rec.getName());

        table.addCell("Address");
        table.addCell(rec.getAddress());

        table.addCell("Amount");
        table.addCell("Rs " + rec.getAmount());

        document.add(table);


        document.add(new Paragraph(" "));


        /* ===== SIGNATURE ===== */

        ImageData signData =
                ImageDataFactory.create(
                        getClass().getClassLoader().getResource("static/images/help1.jpg")
                );

        Image sign = new Image(signData).scaleToFit(120,60);

        document.add(sign);

        Paragraph signText =
                new Paragraph("Ayan Rehan\nPresident\nAmanat Welfare Trust");

        document.add(signText);


        document.add(new Paragraph(" "));


        /* ===== FOOTER ===== */

        Paragraph footer =
                new Paragraph("Thank you for your contribution!")
                        .setFontColor(ColorConstants.GRAY);

        document.add(footer);


        document.close();
    }

}