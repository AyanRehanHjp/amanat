package com.trust.amanat.service;

import com.trust.amanat.dto.AdminLoginDTO;
import com.trust.amanat.entity.AdminEntity;

public interface AdminService {
    AdminEntity createAdmin(AdminEntity admin);
    public String verifyAdminLogin(AdminLoginDTO adminLoginDTO) ;

    }