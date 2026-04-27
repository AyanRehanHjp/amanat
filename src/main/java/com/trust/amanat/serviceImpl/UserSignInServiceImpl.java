package com.trust.amanat.serviceImpl;

import com.trust.amanat.dto.LogInDTO;
import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.repository.UserSignInRepository;
import com.trust.amanat.service.UserSignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserSignInServiceImpl implements UserSignInService {
    @Autowired
    UserSignInRepository userSignInRepository;
    @Autowired
    JWTService jwtService;

    public  String verifyLogIn(LogInDTO loginDTO){
        List<UserEntity> record = userSignInRepository.findByUserName(loginDTO.getUserName());
        if (record.size()>1){
            throw new RuntimeException("This username has multiple records, Please SignUp with new username.");
        }
        if(!record.isEmpty()){
            UserEntity user = record.get(0);
            if(BCrypt.checkpw(loginDTO.getPassword(),user.getPassword())){
                return  jwtService.generateToken(user.getUserName());

            }
        }
        return null;

    }
    public Long getUserId(String userName){
        List<UserEntity> record = userSignInRepository.findByUserName(userName);

        if(!record.isEmpty()){
            return record.get(0).getId();
        }

        return null;
    }
    public UserEntity getUser(String userName){

        List<UserEntity> record = userSignInRepository.findByUserName(userName);

        if(!record.isEmpty()){
            return record.get(0);
        }

        return null;
    }
}
