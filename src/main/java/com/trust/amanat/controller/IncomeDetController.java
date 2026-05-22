package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.IncomeDetDTO;
import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.service.IncomeDetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping ("/incomeDet")
public class IncomeDetController {

    private static final Logger logger =LoggerFactory.getLogger(IncomeDetController.class);
    @Autowired
    IncomeDetService incomeDetService;
    @PostMapping("/addPayment")
    public ResponseEntity<String> addPayment(@RequestBody IncomeDetDTO incomeDetDTO) {

        try {

            String response = incomeDetService.addPayment(incomeDetDTO);
                logger.info("addPayment method is calling");
            if (response.startsWith("AWTIN")) {
                logger.info("Payment added successfully for memberId={}, amount={}, for month={}, and year={}",
                        incomeDetDTO != null ? incomeDetDTO.getMemberId() : null,
                        incomeDetDTO != null ? incomeDetDTO.getAmount() : null,
                        incomeDetDTO != null ? incomeDetDTO.getForMonth() : null,
                        incomeDetDTO != null ? incomeDetDTO.getForYear() : null);
                return new ResponseEntity<>(response, HttpStatus.CREATED);            }

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
logger.error("Error occurred while adding payment: {}", e.getMessage(), e);
            return new ResponseEntity<>(AppConstants.Message.PAYMENT_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/monthly-report")
    public List<Object[]> getMonthlyReport(@RequestParam int year) {
        logger.info("monthly-report called for year={}", year);
        return incomeDetService.getMonthlyReportByYear(year);
    }

    @GetMapping("/my-monthly-report")
    public List<Object[]> getMyMonthlyReport(@RequestParam int year) {

        logger.info("my-monthly-report called for year={}", year);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the user is valid (not anonymous)
        if (!(principal instanceof UserEntity)) {

            logger.error("User not authenticated properly");
            throw new RuntimeException(AppConstants.Message.USER_NOT_AUTHENTICATED);
        }

        UserEntity user = (UserEntity) principal;
        String memberId = user.getMemberId();

        logger.info("Fetching report for memberId={}", memberId);

        List<Object[]> result =
        incomeDetService.getMonthlyReportByYearAndMember(year, memberId);
        logger.info("Total records fetched = {}", result != null ? result.size() : 0);
        return result;
    }

    @GetMapping("/searchMember")
    public List<Object[]> searchMember(@RequestParam String value) {
        logger.info("searchMember called with value={}", value);
        return incomeDetService.searchMember(value);
    }
}
