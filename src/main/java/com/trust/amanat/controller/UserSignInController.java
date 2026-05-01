package com.trust.amanat.controller;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.LogInDTO;
import com.trust.amanat.dto.TokenResponseDTO;
import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.service.UserSignInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signIn")
public class UserSignInController {
    public static final Logger logger = LoggerFactory.getLogger(UserSignInController.class);
    @Autowired
    private UserSignInService userSignInService;
    @PostMapping("/verifyLogin")
    public ResponseEntity<Object> verifyLogin(@RequestBody LogInDTO loginDTO) {
        try {


            String token = userSignInService.verifyLogIn(loginDTO);
            logger.info("verifyLogin method is called for userName={}", loginDTO != null ? loginDTO.getUserName() : null);
            if (token != null) {
                Long userId = userSignInService.getUserId(loginDTO.getUserName());
                UserEntity user = userSignInService.getUser(loginDTO.getUserName());
                TokenResponseDTO tokenResp = new TokenResponseDTO();
                tokenResp.setToken(token);
                tokenResp.setUserId( userId);
                tokenResp.setFirstName(user.getFirstName());
                tokenResp.setLastName(user.getLastName());
                tokenResp.setUserName(user.getUserName());
                tokenResp.setRole(user.getRole());
                logger.info("User logged in successfully: userName={}, userId={}", loginDTO != null ? loginDTO.getUserName() : null, userId);
                return new ResponseEntity<>(tokenResp, HttpStatus.OK);

            }
            logger.warn("Invalid login attempt for userName={}", loginDTO != null ? loginDTO.getUserName() : null);
            return new ResponseEntity<>(AppConstants.Message.INVALID_CREDENTIALS, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            logger.error("Error occurred while verifying login for userName={}: error={}", loginDTO != null ? loginDTO.getUserName() : null, ex.getMessage(), ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST) ;
        }
    }

}
