package com.trust.amanat.service;

import com.trust.amanat.dto.IncomeDetDTO;
import com.trust.amanat.entity.IncomeDetEntity;

import java.util.List;

public interface IncomeDetService {
    public String addPayment(IncomeDetDTO incomeDetDTO);
    public List<Object[]> getMonthlyReportByYear(int year) ;
    public List<Object[]> getMonthlyReportByYearAndMember(int year, String memberId);

    }
