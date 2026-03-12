package com.trust.amanat.serviceImpl;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.trust.amanat.dto.RecPdfGenDTO;
import com.trust.amanat.entity.RecPdfGenEntity;
import com.trust.amanat.repository.RecPdfGenRepository;
import com.trust.amanat.service.RecpdfgenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RecpdfgenServiceImpl implements RecpdfgenService {



        @Autowired
        private RecPdfGenRepository recPdfGenRepository;

        @Override
        public boolean checkReceipt(String receiptNo){

            return recPdfGenRepository
                    .findByReceiptNo(receiptNo)
                    .isPresent();
        }

        @Override
        public RecPdfGenEntity saveReceipt(RecPdfGenDTO dto){

            RecPdfGenEntity rec = new RecPdfGenEntity();

            rec.setName(dto.getName());
            rec.setAddress(dto.getAddress());
            rec.setAmount(dto.getAmount());
            rec.setReceiptNo(dto.getReceiptNo());
            rec.setRecDate(dto.getRecDate());

            return recPdfGenRepository.save(rec);
        }

        @Override
        public void generatePdf(String receiptNo,
                                HttpServletResponse response) throws Exception {

            RecPdfGenEntity rec =
                    recPdfGenRepository
                            .findByReceiptNo(receiptNo)
                            .orElse(null);
            if(rec == null){
                throw new RuntimeException("Receipt not found");
            }

            response.setContentType("application/pdf");

            response.setHeader("Content-Disposition",
                    "attachment; filename=receipt.pdf");

            PdfWriter writer =
                    new PdfWriter(response.getOutputStream());

            PdfDocument pdf =
                    new PdfDocument(writer);

            Document document =
                    new Document(pdf);

            document.add(new Paragraph("AMANAT WELFARE TRUST"));

            document.add(new Paragraph("Receipt"));

            document.add(new Paragraph("Receipt No : "
                    +rec.getReceiptNo()));

            document.add(new Paragraph("Name : "
                    +rec.getName()));

            document.add(new Paragraph("Address : "
                    +rec.getAddress()));

            document.add(new Paragraph("Amount : "
                    +rec.getAmount()));

            document.add(new Paragraph("Receipt Date : "
                    +rec.getRecDate()));

            document.close();



    }
}
