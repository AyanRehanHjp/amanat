package com.trust.amanat.controller;

import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.service.UserSignUpService;
import com.trust.amanat.dto.SignUpDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping ("/signUp")
public class UserSignUpController {
    @Autowired
    private UserSignUpService userSignUpService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody SignUpDTO signUpDTO) {
        try {
            UserEntity response = userSignUpService.addUser(signUpDTO);
            if (response != null) {
                Map<String, Object> userData = new HashMap<>();
                userData.put("msg", "You have successfully signed up");
                userData.put("userId", response.getId());
                return new ResponseEntity<>(userData, HttpStatus.CREATED);
            }

            return new ResponseEntity<>("Saved Failed", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping(value = "/updateUser/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @ModelAttribute SignUpDTO signUpDTO,
                                             @RequestPart(value = "profileImage", required = false) MultipartFile file) {
        try {
            UserEntity updated = userSignUpService.updateUser(id, signUpDTO, file);
            if (updated != null) {
                return new ResponseEntity<>("Updated successfully", HttpStatus.OK);

            }

        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            String deletedId = userSignUpService.deleteUser(id);
            return new ResponseEntity<>(deletedId, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/retriveUser/{id}")
    public ResponseEntity<?> retrieveUser(@PathVariable Long id) {
        try {
            UserEntity user = userSignUpService.retriveUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeProfilePic/{id}")
    public ResponseEntity<String> removeProfilePic(@PathVariable Long id) {
             userSignUpService.removeProfilePic(id);
            return new ResponseEntity<>("Profile picture removed successfully", HttpStatus.OK);
}
}
