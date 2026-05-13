package com.trust.amanat.service;

import com.trust.amanat.dto.ChangePasswordDTO;
import com.trust.amanat.dto.ForgotPasswordDTO;
import com.trust.amanat.entity.UserEntity;

import com.trust.amanat.dto.SignUpDTO;
import org.springframework.web.multipart.MultipartFile;


public interface UserSignUpService {
    public UserEntity addUser(SignUpDTO signUpDTO);
    public UserEntity updateUser (Long    id, SignUpDTO signUpDTO , MultipartFile file);
    public String deleteUser (Long id);
    public UserEntity retriveUser ( long id);
    public void removeProfilePic(Long id);
    public String forgotPassword(ForgotPasswordDTO forgotPasswordDTO);
    public String changePassword(ChangePasswordDTO changePasswordDTO);


    }

