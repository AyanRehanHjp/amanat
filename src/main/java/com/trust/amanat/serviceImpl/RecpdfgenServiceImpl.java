package com.trust.amanat.serviceImpl;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.trust.amanat.common.constants.AppConstants;
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
                throw new RuntimeException(AppConstants.Message.RECEIPT_NOT_FOUND);
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

            document.add(new Paragraph(AppConstants.Message.AMANAT_WELFARE_TRUST));
            document.add(new Paragraph(AppConstants.Message.RECEIPT));
            document.add(new Paragraph(AppConstants.Message.LABEL_RECEIPT_NO +rec.getReceiptNo()));
            document.add(new Paragraph(AppConstants.Message.LABEL_NAME +rec.getName()));
            document.add(new Paragraph(AppConstants.Message.LABEL_ADDRESS +rec.getAddress()));
            document.add(new Paragraph(AppConstants.Message.LABEL_AMOUNT +rec.getAmount()));
            document.add(new Paragraph(AppConstants.Message.LABEL_RECEIPT_DATE +rec.getRecDate()));
            document.close();



    }
}
