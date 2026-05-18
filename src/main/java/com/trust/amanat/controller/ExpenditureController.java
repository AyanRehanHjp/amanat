package com.trust.amanat.controller;

import com.trust.amanat.dto.ExpenditureDTO;
import com.trust.amanat.entity.ExpenditureEntity;
import com.trust.amanat.service.ExpenditureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping ("/expenditure")
public class ExpenditureController {
    public static final Logger logger = LoggerFactory.getLogger(ExpenditureController.class);
    @Autowired
    ExpenditureService expenditureService;

    @PostMapping("/addExpenditure")
    public String addExpenditure(
            @RequestPart("expenditure") ExpenditureDTO expenditureDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        logger.info("Received expenditure request: name={}, amount={}",
                expenditureDTO.getName(), expenditureDTO.getAmount());

        if(file != null && !file.isEmpty()){
            logger.info("File received: {}", file.getOriginalFilename());
            }
            else {
            logger.info("No file uploaded");
            }
            logger.info("Processing expenditure addition for: {}", expenditureDTO.getName());
        return expenditureService.addExpenditure(expenditureDTO, file);
    }


    @GetMapping("/allExpenditure")
    public List <ExpenditureEntity> getAllExpenditure() {
       List <ExpenditureEntity> allExp = expenditureService.getAllExpenditures();
        logger.info("getAllExpenditure method is called, total expenditures found: {}", allExp != null ? allExp.size() : 0);
       return allExp;
    }

    @GetMapping("/receiptGenGetById/{id}")
    public ExpenditureEntity receiptGenGetById(@PathVariable Long id){

        return expenditureService.getAllExpenditures()
                .stream()
                .filter(exp -> exp.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}

