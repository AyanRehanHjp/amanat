package com.trust.amanat.serviceImpl;

import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.repository.RegMembersRepository;
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
    @Autowired
    RegMembersRepository regMembersRepository;

    @Override
    public UserEntity addUser(SignUpDTO signUpDTO) {

        MembersEntity last = regMembersRepository.findTopByOrderByIdDesc();

        String memberId = "AWT001";

        if (last != null && last.getMemberId() != null) {
            int num = Integer.parseInt(last.getMemberId().replace("AWT", ""));
            memberId = String.format("AWT%03d", num + 1);
        }

        if (userSignUpRepository.existsByUserName(signUpDTO.getUserName())) {
            throw new RuntimeException("UserName already exists");
        }

        if (signUpDTO.getEmail() != null && userSignUpRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (signUpDTO.getMobile() != null && userSignUpRepository.existsByMobile(signUpDTO.getMobile())) {
            throw new RuntimeException("Mobile already exists");
        }

        // USER
        UserEntity signUp = new UserEntity();
        signUp.setFirstName(signUpDTO.getFirstName());
        signUp.setLastName(signUpDTO.getLastName());
        signUp.setUserName(signUpDTO.getUserName());
        signUp.setPassword(BCrypt.hashpw(signUpDTO.getPassword(), BCrypt.gensalt(10)));
        signUp.setMobile(signUpDTO.getMobile());
        signUp.setEmail(signUpDTO.getEmail());
        signUp.setRole(signUpDTO.getRole());

        // MEMBER
        MembersEntity member = new MembersEntity();
        member.setFirstname(signUpDTO.getFirstName());
        member.setLastname(signUpDTO.getLastName());
        member.setMobile(signUpDTO.getMobile());
        member.setAddress(signUpDTO.getCity());
        member.setMemberId(memberId);
        member.setJoiningYear(java.time.Year.now().getValue());
        member.setStatus("INACTIVE");

        // LINK
        signUp.setMemberId(memberId);
        signUp.setMember(member);

        return userSignUpRepository.save(signUp);
    }
    @Override
    public UserEntity updateUser(Long id, SignUpDTO signUpDTO, MultipartFile file) {

        UserEntity user = userSignUpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not found"));

        // ================= MOBILE CHECK =================
        String newMobile = signUpDTO.getMobile();
        if (newMobile != null) {
            if (!Objects.equals(newMobile, user.getMobile()) &&
                    userSignUpRepository.existsByMobile(newMobile)) {
                throw new RuntimeException("Mobile already exists to another one");
            }
            user.setMobile(newMobile);
        }

        // ================= EMAIL CHECK =================
        String newEmail = signUpDTO.getEmail();
        if (newEmail != null) {
            if (!Objects.equals(newEmail, user.getEmail()) &&
                    userSignUpRepository.existsByEmail(newEmail)) {
                throw new RuntimeException("Email already exists to another one");
            }
            user.setEmail(newEmail);
        }

        // ================= BASIC FIELDS =================
        if (signUpDTO.getFirstName() != null) {
            user.setFirstName(signUpDTO.getFirstName());
        }

        if (signUpDTO.getLastName() != null) {
            user.setLastName(signUpDTO.getLastName());
        }

        user.setGender(signUpDTO.getGender());
        user.setCity(signUpDTO.getCity());
        user.setDateOfJoining(signUpDTO.getDateOfJoining());
        user.setJoinedBy(signUpDTO.getJoinedBy());
        user.setCountry(signUpDTO.getCountry());
        user.setState(signUpDTO.getState());
        user.setPinCode(signUpDTO.getPinCode());

        // ================= FILE UPLOAD =================
        if (file != null && !file.isEmpty()) {
            try {
                String folder = "uploads/profile/";
                File dir = new File(folder);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(folder + fileName);
                Files.write(path, file.getBytes());

                user.setProfilePicture(fileName);

            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile picture", e);
            }
        }

        // ============================================================
        // 🔥 ADDED: MEMBER SYNC (User update ke saath member bhi update)
        // ============================================================
        MembersEntity member = user.getMember();

        if (member != null) {

            // firstname sync
            if (signUpDTO.getFirstName() != null) {
                member.setFirstname(signUpDTO.getFirstName());
            }

            // lastname sync
            if (signUpDTO.getLastName() != null) {
                member.setLastname(signUpDTO.getLastName());
            }

            // mobile sync
            if (signUpDTO.getMobile() != null) {
                member.setMobile(signUpDTO.getMobile());
            }

            // address sync (city use kar rahe ho)
            if (signUpDTO.getCity() != null) {
                member.setAddress(signUpDTO.getCity());
            }
        }

        // ================= FINAL SAVE =================
        return userSignUpRepository.save(user);
    }

    @Override
    public String deleteUser(Long id) {

        UserEntity user = userSignUpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 ADDED: member reference nikaalo
        MembersEntity member = user.getMember();

        // 🔥 ADDED: relation break karo (important)
        if (member != null) {
            user.setMember(null);
        }

        // USER delete
        userSignUpRepository.delete(user);

        // 🔥 ADDED: member delete
        if (member != null) {
            regMembersRepository.delete(member);
        }

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
