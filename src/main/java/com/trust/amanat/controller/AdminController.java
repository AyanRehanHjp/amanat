package com.trust.amanat.controller;

import com.trust.amanat.dto.AdminLoginDTO;
import com.trust.amanat.dto.TokenResponseDTO;
import com.trust.amanat.entity.AdminEntity;
import com.trust.amanat.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody AdminEntity admin) {
        try {
            AdminEntity saved = adminService.createAdmin(admin);
            return ResponseEntity.ok("Congratulation!, Admin with User Id " + saved.getUserId() + " Created Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
        @PostMapping("/login")
    public ResponseEntity<?> verifyAdminLogin(@RequestBody AdminLoginDTO adminLoginDTO){
            String token = adminService.verifyAdminLogin(adminLoginDTO);
            if(token!=null) {
                TokenResponseDTO tok = new TokenResponseDTO();
                tok.setToken(token);
                tok.setRole("ADMIN");

                return ResponseEntity.ok(tok);
            }
            return ResponseEntity.badRequest().body("Invalid credentials, Try again with correct credential");
}

@GetMapping("/getAllAdmins")
    public ResponseEntity<?> getAllAdmins(){
       List<AdminEntity> allAdmins= adminService.getAllAdmins();
       if (allAdmins!=null){
           return new ResponseEntity<>(allAdmins, HttpStatus.OK);
       }
       return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }
}