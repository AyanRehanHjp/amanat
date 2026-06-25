package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.dto.ChangePasswordDTO;
import com.trust.amanat.dto.ForgotPasswordDTO;
import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.repository.RegMembersRepository;
import com.trust.amanat.repository.UserSignUpRepository;
import com.trust.amanat.service.CloudinaryService;
import com.trust.amanat.service.UserSignUpService;
import com.trust.amanat.dto.SignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    CloudinaryService cloudinaryService;
    @Override
    @Transactional
    public UserEntity addUser(SignUpDTO signUpDTO) {
        MembersEntity last = regMembersRepository.findTopByOrderByIdDesc();
        String memberId = AppConstants.Message.DEFAULT_MEMBER_ID;
        if (last != null && last.getMemberId() != null) {
            int num = Integer.parseInt(last.getMemberId().replace(AppConstants.Message.AWT, ""));
            memberId = String.format(AppConstants.Message.AWT_PLUS_MEM_ID_FORMAT, num + 1);
        }
        if (userSignUpRepository.existsByUserName(signUpDTO.getUserName())) {
            throw new RuntimeException(AppConstants.Validation.USERNAME_ALREADY_EXISTS);
        }

        if (signUpDTO.getEmail() != null && userSignUpRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new RuntimeException(AppConstants.Validation.EMAIL_ALREADY_EXISTS);
        }

        if (signUpDTO.getMobile() != null && userSignUpRepository.existsByMobile(signUpDTO.getMobile())) {
            throw new RuntimeException(AppConstants.Validation.MOBILE_ALREADY_EXISTS);
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
        signUp.setApprovalFlag(AppConstants.Message.Flag_PENDING);


        // MEMBER
        MembersEntity member = new MembersEntity();
        member.setFirstname(signUpDTO.getFirstName());
        member.setLastname(signUpDTO.getLastName());
        member.setMobile(signUpDTO.getMobile());
        member.setAddress(signUpDTO.getCity());
        member.setMemberId(memberId);
        member.setJoiningYear(java.time.Year.now().getValue());
        member.setStatus(AppConstants.Message.INACTIVE);
        member.setApprovalFlag(AppConstants.Message.Flag_PENDING);

        // LINK
        signUp.setMemberId(memberId);
        signUp.setMember(member);

        return userSignUpRepository.save(signUp);
    }
    @Override
    @Transactional
    public UserEntity updateUser(Long id, SignUpDTO signUpDTO, MultipartFile file) {

        UserEntity user = userSignUpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(AppConstants.Validation.USER_NOT_FOUND));

        // ================= MOBILE CHECK =================
        String newMobile = signUpDTO.getMobile();
        if (newMobile != null) {
            if (!Objects.equals(newMobile, user.getMobile()) &&
                    userSignUpRepository.existsByMobile(newMobile)) {
                throw new RuntimeException(AppConstants.Validation.MOBILE_ALREADY_EXISTS_TO_ANOTHER);
            }
            user.setMobile(newMobile);
        }

        // ================= EMAIL CHECK =================
        String newEmail = signUpDTO.getEmail();
        if (newEmail != null) {
            if (!Objects.equals(newEmail, user.getEmail()) &&
                    userSignUpRepository.existsByEmail(newEmail)) {
                throw new RuntimeException(AppConstants.Validation.EMAIL_ALREADY_EXISTS_TO_ANOTHER);
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

                String imageUrl = cloudinaryService.uploadFile(file, AppConstants.Message.PROFILE_PIC);
                user.setProfilePicture(imageUrl);

            } catch (Exception e) {
                throw new RuntimeException(
                        AppConstants.Message.FAILED_TO_UPLOAD_PROFILE_PIC,
                        e
                );
            }
        }

        //MEMBER SYNC (User update ke saath member bhi update)
        // ============================================================
        MembersEntity member = user.getMember();

        if (member != null) {
            if (signUpDTO.getFirstName() != null) {
                member.setFirstname(signUpDTO.getFirstName());
            }
            if (signUpDTO.getLastName() != null) {
                member.setLastname(signUpDTO.getLastName());
            }
            if (signUpDTO.getMobile() != null) {
                member.setMobile(signUpDTO.getMobile());
            }
            if (signUpDTO.getCity() != null) {
                member.setAddress(signUpDTO.getCity());
            }
        }

        // ================= FINAL SAVE =================
        return userSignUpRepository.save(user);
    }

    @Override
    @Transactional
    public String deleteUser(Long id) {

        UserEntity user = userSignUpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(AppConstants.Validation.USER_NOT_FOUND));
        MembersEntity member = user.getMember();
        if (member != null) {
            user.setMember(null);
        }
        userSignUpRepository.delete(user);
        if (member != null) {
            regMembersRepository.delete(member);
        }

        return AppConstants.Message.DELETED_SUCCESSFULLY;
    }

    @Override
    public UserEntity retriveUser(long id) {
        Optional<UserEntity> user = userSignUpRepository.findById(id);
        if (user.isPresent()) {
            return user.get();

        } else throw new RuntimeException(AppConstants.Validation.USER_NOT_FOUND_WITH_THIS_ID + id);
    }
    @Override
    @Transactional
    public void removeProfilePic(Long id){

        UserEntity user = userSignUpRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(AppConstants.Validation.USER_NOT_FOUND));
        if(user.getProfilePicture() != null){

            cloudinaryService.deleteFile(
                    user.getProfilePicture()
            );
        }
        user.setProfilePicture(null);
        userSignUpRepository.save(user);
    }

    @Override
    @Transactional
    public String forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        try{
            if (forgotPasswordDTO == null){
                return AppConstants.Validation.REQUEST_BODY_EMPTY;
            }
            if(forgotPasswordDTO.getUserName() == null || forgotPasswordDTO.getUserName().trim().isEmpty()){
                return AppConstants.Validation.USERNAME_REQUIRED;
            }

            if(forgotPasswordDTO.getNewPassword() == null || forgotPasswordDTO.getNewPassword().trim().isEmpty()){
                return AppConstants.Validation.NEW_PASSWORD_REQUIRED;

            }
            UserEntity user =userSignUpRepository.findByUserName(forgotPasswordDTO.getUserName().trim());
            if(user == null){
                return AppConstants.Validation.USER_NOT_FOUND;

            }
            user.setPassword(BCrypt.hashpw(forgotPasswordDTO.getNewPassword(), BCrypt.gensalt(10)));
            userSignUpRepository.save(user);
            return AppConstants.Validation.PASSWORD_RESET_SUCCESS;

        }
        catch (Exception e){
            e.printStackTrace();
            return AppConstants.Validation.PASSWORD_RESET_FAILED;
        }
    }

        @Override
        @Transactional
        public String changePassword(ChangePasswordDTO changePasswordDTO) {

            try {
                if (changePasswordDTO == null) {
                    return AppConstants.Validation.REQUEST_BODY_EMPTY;

                }
                if (changePasswordDTO.getCurrentPassword() == null || changePasswordDTO.getCurrentPassword().trim().isEmpty()) {
                    return AppConstants.Validation.CURRENT_PASSWORD_REQUIRED;

                }
                if (changePasswordDTO.getNewPassword() == null || changePasswordDTO.getNewPassword().trim().isEmpty()) {
                    return AppConstants.Validation.NEW_PASSWORD_REQUIRED;

                }
                UserEntity user =(UserEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                System.out.println(user);

                if (!BCrypt.checkpw(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
                    return AppConstants.Validation.CURRENT_PASSWORD_INCORRECT;

                }

                user.setPassword(BCrypt.hashpw(changePasswordDTO.getNewPassword(), BCrypt.gensalt(10)));
                userSignUpRepository.save(user);
                return AppConstants.Validation.PASSWORD_CHANGE_SUCCESS;

            } catch (Exception e) {
                e.printStackTrace();
                return AppConstants.Validation.PASSWORD_CHANGE_FAILED;

            }
        }
}
