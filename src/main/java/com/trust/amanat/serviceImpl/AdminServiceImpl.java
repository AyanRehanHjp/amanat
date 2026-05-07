package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.AdminLoginDTO;
import com.trust.amanat.entity.AdminEntity;
import com.trust.amanat.repository.AdminRepository;
import com.trust.amanat.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired JWTService jwtService;
    @Override
    public AdminEntity createAdmin(AdminEntity admin) {
        admin.setUserId(admin.getUserId().trim().toUpperCase());
        // normalize userId to avoid duplicate case issues

        if(adminRepository.existsByUserId(admin.getUserId())){
            throw new RuntimeException(AppConstants.Message.ADMIN_ID_ALREADY_EXISTS);
        }

        admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));
        admin.setStatus("ACTIVE");
        return adminRepository.save(admin);
    }

    public String verifyAdminLogin(AdminLoginDTO adminLoginDTO) {
        AdminEntity admin = adminRepository.findByUserId(adminLoginDTO.getUserId());
    if (admin != null){
        if(admin.getStatus().equals("RESIGNED")){
            return null;
        }
        if (BCrypt.checkpw(adminLoginDTO.getPassword(), admin.getPassword())){
            return jwtService.generateToken(admin.getUserId(), admin.getRole());

        }
    }
    return null;
    }
    public List<AdminEntity> getAllAdmins(){
        return  adminRepository.findAll();
    }

    public void resign(Long id){
        AdminEntity admin = adminRepository.findById(id).get();
        admin.setStatus("PENDING");
        adminRepository.save(admin);
    }

    @Override
    public void acceptResignation(Long id){

        AdminEntity admin = adminRepository.findById(id).get();
        admin.setStatus("RESIGNED");
        adminRepository.save(admin);
    }
}