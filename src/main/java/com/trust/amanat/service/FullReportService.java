package com.trust.amanat.service;

import java.util.List;
import java.util.Map;

public interface FullReportService {
    Double getTotalExpenditure();
    Double getTotalIncome();
    List<Map<String, Object>> getYearlyReport();


    }