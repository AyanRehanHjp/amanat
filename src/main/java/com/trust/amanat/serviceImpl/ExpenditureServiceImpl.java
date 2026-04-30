package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.ExpenditureDTO;
import com.trust.amanat.entity.ExpenditureEntity;
import com.trust.amanat.repository.ExpenditureRepository;
import com.trust.amanat.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ExpenditureServiceImpl implements ExpenditureService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenditureServiceImpl.class);

    @Autowired
    ExpenditureRepository expenditureRepository;

    @Override
    public List<ExpenditureEntity> getAllExpenditures() {
        List<ExpenditureEntity> allExpend = expenditureRepository.findAll();
        return allExpend;
    }

    public String addExpenditure(ExpenditureDTO expenditureDTO) {

        logger.info("Saving expenditure started: name={}, amount={}, year={}, receiptNo={}",
                expenditureDTO != null ? expenditureDTO.getName() : null,
                expenditureDTO != null ? expenditureDTO.getAmount() : null,
                expenditureDTO != null ? expenditureDTO.getYear() : null,
                expenditureDTO != null ? expenditureDTO.getReceiptNo() : null);

        ExpenditureEntity expenditure = new ExpenditureEntity();
        if(expenditureDTO != null) {


            if (expenditureDTO.getName() != null && !expenditureDTO.getName().isEmpty()) {
                expenditure.setName(expenditureDTO.getName());
            } else {
                logger.warn("Validation failed while saving expenditure: Name is required");
                return AppConstants.Validation.NAME_REQUIRED;
            }
            if (expenditureDTO.getAddress() != null && !expenditureDTO.getAddress().isEmpty()) {
                expenditure.setAddress(expenditureDTO.getAddress());
            } else {
                logger.warn("Validation failed while saving expenditure: Address is required");
                return AppConstants.Validation.ADDRESS_REQUIRED;
            }
            if (expenditureDTO.getAmount() != null && expenditureDTO.getAmount() > 0) {
                expenditure.setAmount(expenditureDTO.getAmount());
            } else {
                logger.warn("Validation failed while saving expenditure: Amount is required or invalid");
                return AppConstants.Validation.AMOUNT_REQUIRED;
            }
            if (expenditureDTO.getExpDate() != null) {
                expenditure.setExpDate(expenditureDTO.getExpDate());
            } else {
                logger.warn("Validation failed while saving expenditure: Expenditure date is required");
                return AppConstants.Validation.EXP_DATE_REQUIRED;
            }
            if (expenditureDTO.getYear() != 0 && expenditureDTO.getYear() > 0) {
                expenditure.setYear(expenditureDTO.getYear());
            } else {
                logger.warn("Validation failed while saving expenditure: Year is required or invalid");
                return AppConstants.Validation.YEAR_REQUIRED;
            }
            if (expenditureDTO.getReceiptNo() != null && !expenditureDTO.getReceiptNo().isEmpty()) {
                expenditure.setReceiptNo(expenditureDTO.getReceiptNo());
            } else {
                logger.warn("Validation failed while saving expenditure: Receipt number is required");
                return AppConstants.Validation.RECEIPT_REQUIRED;
            }
        }
        else {
            logger.warn("Validation failed while saving expenditure: Expenditure data is null");
            return AppConstants.Validation.EXP_DATA_REQUIRED;

        }
        ExpenditureEntity saved = expenditureRepository.save(expenditure);
        logger.info("Saving expenditure completed: id={} name={}", saved != null ? saved.getId() : null, saved != null ? saved.getName() : null);
        return AppConstants.Message.EXP_ADDED_SUCCESSFULLY;
    }


}
