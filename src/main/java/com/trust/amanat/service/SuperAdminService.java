package com.trust.amanat.service;

import com.trust.amanat.dto.SuperAdminLoginDto;
import com.trust.amanat.entity.SuperAdminEntity;

public interface SuperAdminService {
    public String verifyLogin (SuperAdminLoginDto superAdminLoginDto) ;

    SuperAdminEntity createSuperAdmin(SuperAdminEntity superAdminEntity);
}
