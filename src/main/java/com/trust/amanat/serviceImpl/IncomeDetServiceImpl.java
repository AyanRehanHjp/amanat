package com.trust.amanat.serviceImpl;
import com.trust.amanat.dto.IncomeDetDTO;
import com.trust.amanat.entity.IncomeDetEntity;
import com.trust.amanat.repository.IncomeDetRepository;
import com.trust.amanat.service.IncomeDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeDetServiceImpl implements IncomeDetService {
    private static final Logger logger = LoggerFactory.getLogger(IncomeDetServiceImpl.class);
    @Autowired
    IncomeDetRepository incomeDetRepository;

    @Override
    public String addPayment(IncomeDetDTO incomeDetDTO) {

        try {

            if (incomeDetDTO == null) {
                return "Request body is empty";
            }

            if (incomeDetDTO.getMemberId() == null) {
                return "Member Id is required";
            }

            if (incomeDetDTO.getAmount() == null) {
                return "Amount is required";
            }

            if (incomeDetDTO.getAmount() <= 0) {
                return "Amount must be greater than zero";
            }

            if (incomeDetDTO.getForMonth() == null || incomeDetDTO.getForMonth().trim().isEmpty()) {
                return "Month is required";
            }

            if (incomeDetDTO.getForYear() == null) {
                return "Year is required";
            }


            IncomeDetEntity incomeDet = new IncomeDetEntity();

            incomeDet.setMemberId(incomeDetDTO.getMemberId());
            double amount = Math.round(incomeDetDTO.getAmount() * 100.0) / 100.0;
            incomeDet.setAmount(amount);
            incomeDet.setForMonth(incomeDetDTO.getForMonth().trim());
            incomeDet.setForYear(incomeDetDTO.getForYear());
            incomeDet.setPaymentDate(LocalDate.now());

            incomeDetRepository.save(incomeDet);

            return "SUCCESS";

        } catch (Exception e) {
            e.printStackTrace();
            return "Database error occurred";
        }
    }

    @Override
    public List<Object[]> getMonthlyReportByYear(int year) {
        return incomeDetRepository.getMonthlyReportByYear(year);
    }
    @Override
    public List<Object[]> getMonthlyReportByYearAndMember(int year, String memberId) {
        logger.info("➡️ Service: Fetching monthly report for year={} and memberId={}", year, memberId);

        List<Object[]> data = incomeDetRepository.getMonthlyReportByYearAndMember(year, memberId);
        if (data == null || data.isEmpty()) {
            logger.warn("No data found for memberId={} and year={}", memberId, year);
        } else {
            logger.info("Service: Data fetched successfully, records count={}", data.size());
        }
        return data;
    }
    @Override
    public List<Object[]> searchMember(String value) {
        return incomeDetRepository.searchMember(value);
    }
}