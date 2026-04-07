package com.trust.amanat.controller;

import com.trust.amanat.dto.ExpenditureDTO;
import com.trust.amanat.entity.ExpenditureEntity;
import com.trust.amanat.service.ExpenditureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/expenditure")
public class ExpenditureController {
    public static final Logger logger = LoggerFactory.getLogger(ExpenditureController.class);
    @Autowired
    ExpenditureService expenditureService;

    @PostMapping ("/addExpenditure")
    public String addExpenditure(@RequestBody ExpenditureDTO expenditureDTO) {
        logger.info("addExpenditure method is called with expenditure details: name={}, amount={}, year={}, receiptNo={}",
                expenditureDTO != null ? expenditureDTO.getName() : null,
                expenditureDTO != null ? expenditureDTO.getAmount() : null,
                expenditureDTO != null ? expenditureDTO.getYear() : null,
                expenditureDTO != null ? expenditureDTO.getReceiptNo() : null);
        String expenditure = expenditureService.addExpenditure(expenditureDTO);
        logger.info("addExpenditure method completed with response: {}", expenditure);
        return expenditure;
    }


    @GetMapping("/allExpenditure")
    public List <ExpenditureEntity> getAllExpenditure() {
       List <ExpenditureEntity> allExp = expenditureService.getAllExpenditures();
        logger.info("getAllExpenditure method is called, total expenditures found: {}", allExp != null ? allExp.size() : 0);
       return allExp;
    }

}

