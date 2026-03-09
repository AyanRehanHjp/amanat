package com.trust.amanat.controller;

import com.trust.amanat.dto.ExpenditureDTO;
import com.trust.amanat.entity.ExpenditureEntity;
import com.trust.amanat.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/expenditure")
public class ExpenditureController {

    @Autowired
    ExpenditureService expenditureService;

    @PostMapping ("/addExpenditure")
    public String addExpenditure(@RequestBody ExpenditureDTO expenditureDTO) {
        String expenditure = expenditureService.addExpenditure(expenditureDTO);
        return expenditure;
    }


    @GetMapping("/allExpenditure")
    public List <ExpenditureEntity> getAllExpenditure() {
       List <ExpenditureEntity> allExp = expenditureService.getAllExpenditures();
       return allExp;
    }
}

