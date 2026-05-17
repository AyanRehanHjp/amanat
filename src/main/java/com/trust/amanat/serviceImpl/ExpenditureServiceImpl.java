package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.ExpenditureDTO;
import com.trust.amanat.entity.ExpenditureEntity;
import com.trust.amanat.repository.ExpenditureRepository;
import com.trust.amanat.service.CloudinaryService;
import com.trust.amanat.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ExpenditureServiceImpl implements ExpenditureService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenditureServiceImpl.class);

    @Autowired
    ExpenditureRepository expenditureRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public List<ExpenditureEntity> getAllExpenditures() {
        List<ExpenditureEntity> allExpend = expenditureRepository.findAll();
        return allExpend;
    }
    @Override
    @Transactional
    public String addExpenditure(ExpenditureDTO dto, MultipartFile file) {

    logger.info("Saving expenditure: {}", dto.getName());

    ExpenditureEntity exp = new ExpenditureEntity();
        ExpenditureEntity lastExp = expenditureRepository.findTopByOrderByIdDesc();
        String nextReceiptNo = "001";
        if(lastExp != null && lastExp.getReceiptNo() != null){
            int num = Integer.parseInt(lastExp.getReceiptNo());
            nextReceiptNo = String.format("%03d", (num + 1));
        }
    exp.setReceiptNo(nextReceiptNo);
    exp.setName(dto.getName());
    exp.setAddress(dto.getAddress());
    exp.setAmount(dto.getAmount());
    exp.setYear(dto.getYear());
//    exp.setReceiptNo(dto.getReceiptNo());
    exp.setProblem(dto.getProblem());
    exp.setExpDate(dto.getExpDate());

    // 🔹 FILE SAVE (same beneficiary logic)
    if(file != null && !file.isEmpty()){

        try {

            String fileUrl =
                    cloudinaryService.uploadFile(
                            file,
                            AppConstants.Message.BENEFICIARY_DOCUMENTS
                    );

            exp.setSupDoc(fileUrl);

            logger.info("File uploaded to Cloudinary successfully");

        } catch (Exception e) {

            logger.error("Cloudinary upload failed: {}", e.getMessage());

            return "File upload failed";
        }
    } else {
        logger.warn("Supportive document not uploaded");
        return "Supportive document is required";
    }

    ExpenditureEntity saved = expenditureRepository.save(exp);

    logger.info("Expenditure saved with id={}", saved.getId());

    return AppConstants.Message.EXP_ADDED_SUCCESSFULLY;
}
}
