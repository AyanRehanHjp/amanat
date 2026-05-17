package com.trust.amanat.service;

import com.trust.amanat.dto.AdminLoginDTO;
import com.trust.amanat.entity.AdminEntity;

import java.util.List;

public interface AdminService {
    AdminEntity createAdmin(AdminEntity admin);
    String verifyAdminLogin(AdminLoginDTO adminLoginDTO) ;
    List<AdminEntity> getAllAdmins();
    public void resign(Long id);
    void acceptResignation(Long id);
    String rejectResignation(Long id);

    }