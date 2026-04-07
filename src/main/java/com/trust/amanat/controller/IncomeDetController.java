package com.trust.amanat.controller;

import com.trust.amanat.dto.IncomeDetDTO;
import com.trust.amanat.entity.ExpenditureEntity;
import com.trust.amanat.entity.IncomeDetEntity;
import com.trust.amanat.service.IncomeDetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            if (response.equals("SUCCESS")) {
                logger.info("Payment added successfully for memberId={}, amount={}, month={}, year={}",
                        incomeDetDTO != null ? incomeDetDTO.getMemberId() : null,
                        incomeDetDTO != null ? incomeDetDTO.getAmount() : null,
                        incomeDetDTO != null ? incomeDetDTO.getMonth() : null,
                        incomeDetDTO != null ? incomeDetDTO.getYear() : null);
                return new ResponseEntity<>("Payment added successfully", HttpStatus.CREATED);
            }

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
logger.error("Error occurred while adding payment: {}", e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong while adding payment",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping ("/showIncomeDet")
    public List <IncomeDetEntity> showIncomeDet(){
        List<IncomeDetEntity> allInc = incomeDetService.showIncomeDet();
        logger.info("showIncomeDet method is called, total income details found: {}", allInc != null ? allInc.size() : 0);
        return allInc;

    }

}
