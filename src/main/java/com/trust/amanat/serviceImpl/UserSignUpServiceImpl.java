package com.trust.amanat.serviceImpl;

import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.repository.UserSignUpRepository;
import com.trust.amanat.service.UserSignUpService;
import com.trust.amanat.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Objects;


@Service
public class UserSignUpServiceImpl implements UserSignUpService {

    @Autowired
    UserSignUpRepository userSignUpRepository;


    @Override
    public UserEntity addUser(SignUpDTO signUpDTO) {
        if (userSignUpRepository.existsByUserName(signUpDTO.getUserName())) {
            throw new RuntimeException("UserName is already exists please try with different name");
        }
        // null-safe checks for email and mobile
        if (signUpDTO.getEmail() != null && userSignUpRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new RuntimeException("Email already registered please register with new email");
        }
        if (signUpDTO.getMobile() != null && userSignUpRepository.existsByMobile(signUpDTO.getMobile())) {
            throw new RuntimeException("Mobile no already Registered, Please choose new mobile");

        }
        UserEntity signUp = new UserEntity();
        signUp.setFirstName(signUpDTO.getFirstName());
        signUp.setLastName(signUpDTO.getLastName());
        signUp.setUserName(signUpDTO.getUserName());
        signUp.setPassword(BCrypt.hashpw(signUpDTO.getPassword(), BCrypt.gensalt(10)));
        signUp.setMobile(signUpDTO.getMobile());
        signUp.setEmail(signUpDTO.getEmail());
        signUp.setRole(signUpDTO.getRole());
        return userSignUpRepository.save(signUp);
    }

    @Override
    public UserEntity updateUser(Long id, SignUpDTO signUpDTO, MultipartFile file) {
        UserEntity user = userSignUpRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));

        // Handle mobile: only validate existence if new mobile provided
        String newMobile = signUpDTO.getMobile();
        if (newMobile != null) {
            // if changed and exists for another user -> error
            if (!Objects.equals(newMobile, user.getMobile()) && userSignUpRepository.existsByMobile(newMobile)) {
                throw new RuntimeException("Mobile already exists to another one");
            }
            user.setMobile(newMobile);
        }

        // Handle email: only validate existence if new email provided
        String newEmail = signUpDTO.getEmail();
        if (newEmail != null) {
            if (!Objects.equals(newEmail, user.getEmail()) && userSignUpRepository.existsByEmail(newEmail)) {
                throw new RuntimeException("Email already exists to another one");
            }
            user.setEmail(newEmail);
        }

        if (signUpDTO.getFirstName() != null) {
            user.setFirstName(signUpDTO.getFirstName());
        }
        if (signUpDTO.getLastName() != null) {
            user.setLastName(signUpDTO.getLastName());
        }
        // mobile/email already handled above
        user.setGender(signUpDTO.getGender());
        user.setCity(signUpDTO.getCity());
        user.setDateOfJoining(signUpDTO.getDateOfJoining());
        user.setJoinedBy(signUpDTO.getJoinedBy());
        user.setCountry(signUpDTO.getCountry());
        user.setState(signUpDTO.getState());
        user.setPinCode(signUpDTO.getPinCode());

        if (file != null && !file.isEmpty()) {
            try {
                String folder = "uploads/profile/";
                File dir = new File(folder);
                if (!dir.exists()) {
                    boolean created = dir.mkdirs();
                    if (!created) {
                        throw new RuntimeException("Failed to create upload directory");
                    }
                }
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(folder + fileName);
                Files.write(path, file.getBytes());
                user.setProfilePicture(fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile picture", e);
            }
        }
        return userSignUpRepository.save(user);


    }

    @Override
    public String deleteUser(Long id) {
        boolean userExists = userSignUpRepository.existsById(id);
        if (!userExists) {
            throw new RuntimeException("User not found");
        }
        userSignUpRepository.deleteById(id);
        return "Deleted Successfully";
    }

    @Override
    public UserEntity retriveUser(long id) {
        Optional<UserEntity> user = userSignUpRepository.findById(id);
        if (user.isPresent()) {
            return user.get();

        } else throw new RuntimeException("User not found with this id: " + id);
    }

    public void removeProfilePic(Long id){

        UserEntity user = userSignUpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setProfilePicture(null);
        userSignUpRepository.save(user);
    }

}
