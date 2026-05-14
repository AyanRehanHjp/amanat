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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RecpdfgenServiceImpl implements RecpdfgenService {
    public static final Logger logger = LoggerFactory.getLogger(RecpdfgenServiceImpl.class);

    @Autowired
    private RecPdfGenRepository recPdfGenRepository;

    @Override
    public boolean checkReceipt(String receiptNo){
        logger.info("Checking if receipt exists for receiptNo: {}", receiptNo);
        boolean recepExists = recPdfGenRepository.findByReceiptNo(receiptNo).isPresent();
        logger.info("Receipt existence for receiptNo {}: {}", receiptNo, recepExists);
        return recepExists;
    }

    @Override
    public RecPdfGenEntity saveReceipt(RecPdfGenDTO dto){
        RecPdfGenEntity rec = new RecPdfGenEntity();
        rec.setName(dto.getName());
        rec.setAddress(dto.getAddress());
        rec.setAmount(dto.getAmount());
        rec.setReceiptNo(dto.getReceiptNo());
        rec.setRecDate(dto.getRecDate());

        RecPdfGenEntity recPdfGen = recPdfGenRepository.save(rec);
        logger.info("Receipt saved with receiptNo: {}", recPdfGen.getReceiptNo());
        return recPdfGen;
    }

    @Override
    public void generatePdf(String receiptNo,HttpServletResponse response) throws Exception {

        RecPdfGenEntity rec =recPdfGenRepository.findByReceiptNo(receiptNo).orElse(null);
        logger.info("Found receipt for Receipt No: {}", receiptNo);
        if(rec == null){
            logger.error("Receipt not found for receiptNo: {}", receiptNo);
            throw new RuntimeException(AppConstants.Message.RECEIPT_NOT_FOUND);
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","attachment; filename=receipt.pdf");
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph(AppConstants.Message.AMANAT_WELFARE_TRUST));
        document.add(new Paragraph(AppConstants.Message.RECEIPT));
        document.add(new Paragraph(AppConstants.Message.LABEL_RECEIPT_NO +rec.getReceiptNo()));
        document.add(new Paragraph(AppConstants.Message.LABEL_NAME +rec.getName()));
        document.add(new Paragraph(AppConstants.Message.LABEL_ADDRESS +rec.getAddress()));
        document.add(new Paragraph(AppConstants.Message.LABEL_AMOUNT +rec.getAmount()));
        document.add(new Paragraph(AppConstants.Message.LABEL_RECEIPT_DATE +rec.getRecDate()));
        document.close();
        logger.info("PDF generated successfully for receiptNo: {}", receiptNo);
        }
}
