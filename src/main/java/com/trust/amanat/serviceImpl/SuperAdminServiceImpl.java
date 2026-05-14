package com.trust.amanat.serviceImpl;

import com.trust.amanat.dto.SuperAdminLoginDto;
import com.trust.amanat.entity.SuperAdminEntity;
import com.trust.amanat.repository.SuperAdminRepository;
import com.trust.amanat.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    SuperAdminRepository superAdminRepository;

    @Autowired
    JWTService  jwtService;


    @Override
    public String verifyLogin (SuperAdminLoginDto superAdminLoginDto) {
       SuperAdminEntity superAdmin = superAdminRepository.findByUsername(superAdminLoginDto.getUsername());
       if(superAdmin!= null){
           if(BCrypt.checkpw(superAdminLoginDto.getPassword(), superAdmin.getPassword())){
               return jwtService.generateToken(superAdmin.getUsername(), "SUPER_ADMIN");
           } else {
               return null;

           }

       }
        return null;

    }

    @Override
    @Transactional
    public SuperAdminEntity createSuperAdmin(
            SuperAdminEntity superAdminEntity) {

        superAdminEntity.setPassword(BCrypt.hashpw(superAdminEntity.getPassword(),BCrypt.gensalt()));
        return superAdminRepository.save(superAdminEntity);
    }

}
