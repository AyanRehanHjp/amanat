package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.AdminLoginDTO;
import com.trust.amanat.dto.TokenResponseDTO;
import com.trust.amanat.entity.AdminEntity;
import com.trust.amanat.service.AdminService;

import com.trust.amanat.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    public static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService adminService;

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody AdminEntity admin) {
        try {
            logger.info("Create Admin API called with admin details: {}", admin);
            AdminEntity saved = adminService.createAdmin(admin);
            logger.info("Admin created successfully with ID: {}", saved.getUserId());
            return ResponseEntity.ok(AppConstants.Message.ADMIN_ID + saved.getUserId() );
        } catch (Exception e) {
            logger.error("Error creating admin: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
        @PostMapping("/login")
    public ResponseEntity<?> verifyAdminLogin(@RequestBody AdminLoginDTO adminLoginDTO){
            boolean validCaptcha = captchaService.verifyCaptcha(adminLoginDTO.getCaptchaId(), adminLoginDTO.getCaptchaValue());
            logger.info("Admin login attempt for userId: {}, captcha valid: {}", adminLoginDTO.getUserId(), validCaptcha);

            if(!validCaptcha){
                logger.error("Invalid captcha for userId: {}", adminLoginDTO.getUserId());
                return ResponseEntity.badRequest().body(AppConstants.Validation.INVALID_CAPTCHA);
            }
            String token = adminService.verifyAdminLogin(adminLoginDTO);
            if(token!=null) {
                TokenResponseDTO tok = new TokenResponseDTO();
                tok.setToken(token);
                tok.setRole(AppConstants.Role.ADMIN);
            logger.info("Admin login successful for userId: {}", adminLoginDTO.getUserId());
                return ResponseEntity.ok(tok);
            }
            logger.error("Admin login failed for userId: {}", adminLoginDTO.getUserId());
            return ResponseEntity.badRequest().body(AppConstants.Message.INVALID_CREDENTIALS);
}

@GetMapping("/getAllAdmins")
    public ResponseEntity<?> getAllAdmins(){
       List<AdminEntity> allAdmins= adminService.getAllAdmins();
       if (allAdmins!=null){
           logger.info("Fetched all admins successfully, count: {}", allAdmins.size());
           return new ResponseEntity<>(allAdmins, HttpStatus.OK);
       }
       logger.info("Failed to fetch admins, no admins found");
       return new ResponseEntity<>(AppConstants.Message.SOMETHING_WRONG, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/resign/{id}")
    public String resign(@PathVariable Long id){
        adminService.resign(id);
        logger.info("Resignation APi Done");
        return AppConstants.Message.RESIGNATION_SENT ;
    }

    @PostMapping("/accept-resignation/{id}")
    public String acceptResignation(@PathVariable Long id){
        logger.info("method accept resignation started");
        adminService.acceptResignation(id);

        logger.info("Resignation Accepted success");
        return AppConstants.Message.RESIGNATION_ACCEPTED ;
    }

    @PostMapping("/reject-resignation/{id}")
    public ResponseEntity<?> rejectResignation(
            @PathVariable Long id){

        String msg =
                adminService.rejectResignation(id);

        return ResponseEntity.ok(msg);
    }
}