package com.trust.amanat.controller;

import com.trust.amanat.service.FullReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class FullReportController {

    @Autowired
    private FullReportService fullReportService;

    @GetMapping("/total-expenditure")
    public ResponseEntity<?> getTotalExpenditure(){
        return ResponseEntity.ok(fullReportService.getTotalExpenditure());
    }

    @GetMapping("/total-income")
    public ResponseEntity<?> getTotalIncome(){
        return ResponseEntity.ok(fullReportService.getTotalIncome());
    }

    @GetMapping("/yearly")
    public ResponseEntity<?> getYearlyReport(){
        return ResponseEntity.ok(fullReportService.getYearlyReport());
    }}