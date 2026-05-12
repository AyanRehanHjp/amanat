package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.SuperAdminLoginDto;
import com.trust.amanat.dto.SuperAdminTokenDTO;
import com.trust.amanat.entity.SuperAdminEntity;
import com.trust.amanat.service.SuperAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin")
public class SuperAdminController {
    public static final Logger logger = LoggerFactory.getLogger(SuperAdminController.class);


    @Autowired
    SuperAdminService superAdminService;

    @PostMapping("/create-super-admin")
    public ResponseEntity<?> createSuperAdmin(@RequestBody SuperAdminEntity superAdminEntity){
        SuperAdminEntity saved = superAdminService.createSuperAdmin(superAdminEntity);
        logger.info("createSuperAdmin method called for username: {}", superAdminEntity != null ? superAdminEntity.getUsername() : null);
        return ResponseEntity.ok(saved);
    }

   @PostMapping("/super-admin-login")
    public ResponseEntity <?> superAdminLogin(@RequestBody SuperAdminLoginDto superAdminLoginDto) {
       String token = superAdminService.verifyLogin(superAdminLoginDto);
       if (token != null) {
           SuperAdminTokenDTO tok = new SuperAdminTokenDTO();
           tok.setToken(token);
           tok.setRole(AppConstants.Message.SUPER_ADMIN);
           logger.info("Super Admin login successful for username: {}", superAdminLoginDto.getUsername());
              return ResponseEntity.ok(tok);
       }

       logger.info("Super Admin login attempt failed for username: {}", superAdminLoginDto.getUsername());
       return ResponseEntity.badRequest().body(AppConstants.Message.INVALID_CREDENTIALS);
   }

}
