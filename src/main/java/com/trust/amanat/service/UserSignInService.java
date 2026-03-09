package com.trust.amanat.service;

import com.trust.amanat.dto.LogInDTO;
import com.trust.amanat.entity.UserEntity;

public interface UserSignInService {
    public  String verifyLogIn(LogInDTO loginDTO);
    public Long getUserId(String userName);
    public UserEntity getUser(String userName);


    }
