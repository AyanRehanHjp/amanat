package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.repository.ExpenditureRepository;
import com.trust.amanat.repository.IncomeDetRepository;
import com.trust.amanat.service.FullReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FullReportServiceImpl implements FullReportService {

    @Autowired
    private ExpenditureRepository expenditureRepository;

    @Override
    public Double getTotalExpenditure(){
        Double total = expenditureRepository.getTotalExpenditure();
        return total != null ? total : 0;
    }

    @Autowired
    private IncomeDetRepository incomeDetRepository;

    public Double getTotalIncome(){
        Double total = incomeDetRepository.getTotalIncome();
        return total != null ? total : 0;
    }

    public List<Map<String, Object>> getYearlyReport(){

        List<Object[]> incomeList = incomeDetRepository.getYearlyIncome();
        List<Object[]> expenseList = expenditureRepository.getYearlyExpense();

        Map<Integer, Double> incomeMap = new HashMap<>();
        Map<Integer, Double> expenseMap = new HashMap<>();

        // 🟢 Income map
        for(Object[] obj : incomeList){
            Integer year = (Integer) obj[0];
            Double amount = (Double) obj[1];
            incomeMap.put(year, amount);
        }

        // 🔴 Expense map
        for(Object[] obj : expenseList){
            Integer year = (Integer) obj[0];
            Double amount = (Double) obj[1];
            expenseMap.put(year, amount);
        }

        // 🔥 Combine
        List<Map<String, Object>> result = new ArrayList<>();

        Set<Integer> allYears = new HashSet<>();
        allYears.addAll(incomeMap.keySet());
        allYears.addAll(expenseMap.keySet());

        for(Integer year : allYears){

            double income = incomeMap.getOrDefault(year, 0.0);
            double expense = expenseMap.getOrDefault(year, 0.0);
            double left = income - expense;

            Map<String, Object> data = new HashMap<>();
            data.put(AppConstants.Message.YEAR, year);
            data.put(AppConstants.Message.INCOME, income);
            data.put(AppConstants.Message.EXPENSE, expense);
            data.put(AppConstants.Message.LEFT_BALANCE, left);

            result.add(data);
        }

        return result;
    }
}