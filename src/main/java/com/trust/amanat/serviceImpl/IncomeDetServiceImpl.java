package com.trust.amanat.serviceImpl;
import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.IncomeDetDTO;
import com.trust.amanat.entity.IncomeDetEntity;
import com.trust.amanat.repository.IncomeDetRepository;
import com.trust.amanat.repository.RegMembersRepository;
import com.trust.amanat.service.IncomeDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeDetServiceImpl implements IncomeDetService {
    private static final Logger logger = LoggerFactory.getLogger(IncomeDetServiceImpl.class);
    @Autowired
    IncomeDetRepository incomeDetRepository;
    @Autowired
    RegMembersRepository regMembersRepository;


    @Override
    @Transactional
    public String addPayment(IncomeDetDTO incomeDetDTO) {

        try {

            if (incomeDetDTO == null) {
                return AppConstants.Validation.REQUEST_BODY_EMPTY;
            }

            if (incomeDetDTO.getMemberId() == null) {
                return AppConstants.Validation.MEMBER_ID_REQUIRED;
            }

            boolean memberExists =  regMembersRepository.existsByMemberId(incomeDetDTO.getMemberId());

            if(!memberExists){
                return AppConstants.Validation.MEMBER_ID_DOES_NOT_EXISTS;

            }

            if (incomeDetDTO.getAmount() == null) {
                return AppConstants.Validation.AMOUNT_REQUIRED;
            }

            if (incomeDetDTO.getAmount() <= 0) {
                return AppConstants.Validation.AMOUNT_NOT_GREATER_ZERO;
            }

            if (incomeDetDTO.getForMonth() == null || incomeDetDTO.getForMonth().trim().isEmpty()) {
                return AppConstants.Validation.MONTH_REQUIRED;
            }

            if (incomeDetDTO.getForYear() == null) {
                return AppConstants.Validation.YEAR_REQUIRED;
            }


            IncomeDetEntity incomeDet = new IncomeDetEntity();

            incomeDet.setMemberId(incomeDetDTO.getMemberId());
            double amount = Math.round(incomeDetDTO.getAmount() * 100.0) / 100.0;
            incomeDet.setAmount(amount);
            incomeDet.setForMonth(incomeDetDTO.getForMonth().trim());
            incomeDet.setForYear(incomeDetDTO.getForYear());
            incomeDet.setPaymentDate(LocalDate.now());
            incomeDet.setComment(incomeDetDTO.getComment());

            IncomeDetEntity lastIncome=incomeDetRepository.findTopByOrderByIdDesc();
            String incReceiptNo="AWTIN1001";
            if(lastIncome!=null && lastIncome.getInc_receipt_no()!=null){
                int num=Integer.parseInt(lastIncome.getInc_receipt_no().replace("AWTIN",""));
                incReceiptNo="AWTIN"+(num+1); }
            incomeDet.setInc_receipt_no(incReceiptNo);
            incomeDetRepository.save(incomeDet);

            return incReceiptNo;

        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.Message.DATABASE_ERROR;
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