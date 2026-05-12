package com.trust.amanat.controller;

import com.trust.amanat.service.FullReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/report")
public class FullReportController {

    private static final Logger logger = LoggerFactory.getLogger(FullReportController.class);

    @Autowired
    private FullReportService fullReportService;

    @GetMapping("/total-expenditure")
    public ResponseEntity<?> getTotalExpenditure(){
        logger.info("getTotalExpenditure called");
        return ResponseEntity.ok(fullReportService.getTotalExpenditure());
    }

    @GetMapping("/total-income")
    public ResponseEntity<?> getTotalIncome(){
        logger.info("getTotalIncome called");
        return ResponseEntity.ok(fullReportService.getTotalIncome());
    }

    @GetMapping("/yearly")
    public ResponseEntity<?> getYearlyReport(){
        logger.info("getYearlyReport called");
        return ResponseEntity.ok(fullReportService.getYearlyReport());
    }}