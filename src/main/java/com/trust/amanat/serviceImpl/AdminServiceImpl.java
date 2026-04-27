package com.trust.amanat.serviceImpl;

import com.trust.amanat.dto.AdminLoginDTO;
import com.trust.amanat.entity.AdminEntity;
import com.trust.amanat.repository.AdminRepository;
import com.trust.amanat.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired JWTService jwtService;
    @Override
    public AdminEntity createAdmin(AdminEntity admin) {

        if(adminRepository.existsByUserId(admin.getUserId())){
            throw new RuntimeException("AdminId already exists");
        }

        admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));

        return adminRepository.save(admin);
    }

    public String verifyAdminLogin(AdminLoginDTO adminLoginDTO) {
        AdminEntity admin = adminRepository.findByUserId(adminLoginDTO.getUserId());
    if (admin != null){
        if (BCrypt.checkpw(adminLoginDTO.getPassword(), admin.getPassword())){
            return jwtService.generateToken(admin.getUserId());

        }
    }
return null;
    }
}