package com.trust.amanat.controller;

import com.trust.amanat.dto.ScanPayDTO;
import com.trust.amanat.entity.ScanPayEntity;
import com.trust.amanat.service.ScanPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scan&pay")
public class ScanPayController {

    @Autowired
    ScanPayService scanPayService;

    @PostMapping("/addPayee")
    public ResponseEntity<String> addPayee(@RequestBody ScanPayDTO scanPayDTO) {
         scanPayService.addPayee(scanPayDTO);
        return new ResponseEntity<>("Payment details submitted Successfully", HttpStatus.CREATED) ;
    }

}
