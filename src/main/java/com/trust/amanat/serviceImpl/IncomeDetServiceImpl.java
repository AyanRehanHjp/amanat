package com.trust.amanat.serviceImpl;
import com.trust.amanat.dto.IncomeDetDTO;
import com.trust.amanat.entity.IncomeDetEntity;
import com.trust.amanat.repository.IncomeDetRepository;
import com.trust.amanat.service.IncomeDetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeDetServiceImpl implements IncomeDetService {
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

            if (incomeDetDTO.getMonth() == null || incomeDetDTO.getMonth().trim().isEmpty()) {
                return "Month is required";
            }

            if (incomeDetDTO.getYear() == null) {
                return "Year is required";
            }

            if (incomeDetDTO.getYear() < 2000 || incomeDetDTO.getYear() > 2100) {
                return "Invalid year";
            }

            IncomeDetEntity incomeDet = new IncomeDetEntity();

            incomeDet.setMemberId(incomeDetDTO.getMemberId());
            incomeDet.setAmount(incomeDetDTO.getAmount());
            incomeDet.setMonth(incomeDetDTO.getMonth().trim());
            incomeDet.setYear(incomeDetDTO.getYear());

            incomeDetRepository.save(incomeDet);

            return "SUCCESS";

        } catch (Exception e) {

            e.printStackTrace();
            return "Database error occurred";
        }
    }
}
