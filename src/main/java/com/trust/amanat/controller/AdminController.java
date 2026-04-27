package com.trust.amanat.controller;

import com.trust.amanat.dto.AdminLoginDTO;
import com.trust.amanat.entity.AdminEntity;
import com.trust.amanat.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody AdminEntity admin){
        AdminEntity saved = adminService.createAdmin(admin);
        return ResponseEntity.ok("Admin Created Successfully");
    }

        @PostMapping("/login")
    public ResponseEntity<?> verifyAdminLogin(@RequestBody AdminLoginDTO adminLoginDTO){
            String token = adminService.verifyAdminLogin(adminLoginDTO);
            if(token!=null) {
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.badRequest().body("Invalid credentials, Try again with correct credential");
}
    }