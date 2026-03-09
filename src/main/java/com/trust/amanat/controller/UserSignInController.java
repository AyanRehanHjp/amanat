package com.trust.amanat.controller;

import com.trust.amanat.dto.LogInDTO;
import com.trust.amanat.dto.TokenResponseDTO;
import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.service.UserSignInService;
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
    @Autowired
    private UserSignInService userSignInService;
    @PostMapping("/verifyLogin")
    public ResponseEntity<Object> verifyLogin(@RequestBody LogInDTO loginDTO) {
        try {


            String token = userSignInService.verifyLogIn(loginDTO);
            if (token != null) {
                Long userId = userSignInService.getUserId(loginDTO.getUserName());
                UserEntity user = userSignInService.getUser(loginDTO.getUserName());
                TokenResponseDTO tokenResp = new TokenResponseDTO();
                tokenResp.setToken(token);
                tokenResp.setUserId( userId);
                tokenResp.setFirstName(user.getFirstName());
                tokenResp.setLastName(user.getLastName());
                tokenResp.setUserName(user.getUserName());
                return new ResponseEntity<>(tokenResp, HttpStatus.OK);

            }
            return new ResponseEntity<>("Invalid credentials, Try again with correct credential", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST) ;
        }
    }

}
