package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.repository.RegMembersRepository;
import com.trust.amanat.repository.UserSignUpRepository;
import com.trust.amanat.service.RegMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegMembersServiceImpl implements RegMembersService {

    @Autowired
    RegMembersRepository regMembersRepository;

    @Autowired
    UserSignUpRepository userSignUpRepository;

    // ================= ADD MEMBER =================
    @Override
    public MembersEntity addMember(MembersEntity member) {

        MembersEntity lastMember = regMembersRepository.findTopByOrderByIdDesc();

        String newMemberId = "AWT001";

        if (lastMember != null && lastMember.getMemberId() != null) {
            String lastId = lastMember.getMemberId();
            String numberPart = lastId.replace(AppConstants.Message.AWT, "");
            int num = Integer.parseInt(numberPart);
            num++;
            newMemberId = AppConstants.Message.AWT + num;
        }

        MembersEntity newMember = new MembersEntity();
        newMember.setPrefix(member.getPrefix());
        newMember.setFirstname(member.getFirstname());
        newMember.setLastname(member.getLastname());
        newMember.setAddress(member.getAddress());
        newMember.setMobile(member.getMobile());
        newMember.setJoinedBy(member.getJoinedBy());
        newMember.setJoiningYear(member.getJoiningYear());
        newMember.setMemberId(newMemberId);
        newMember.setStatus(member.getStatus());

        // 🔥  save
        MembersEntity savedMember = regMembersRepository.save(newMember);

        // 🔥  user save
        UserEntity user = new UserEntity();
        user.setFirstName(savedMember.getFirstname());
        user.setLastName(savedMember.getLastname());
        user.setMobile(savedMember.getMobile());
        user.setMember(savedMember);   // relation

        userSignUpRepository.save(user);

        return savedMember;
    }


    // ================= GET ALL =================
    @Override
    public List<MembersEntity> getAllMembers() {

        return regMembersRepository.findAll();
    }


    // ================= UPDATE MEMBER =================
    @Override
    public MembersEntity updateMember(String memberId, MembersEntity member){

        MembersEntity members = regMembersRepository.findByMemberId(memberId);

        if(members == null){
            throw new RuntimeException(AppConstants.Message.MEMBER_NOT_FOUND);
        }

        members.setPrefix(member.getPrefix());
        members.setFirstname(member.getFirstname());
        members.setLastname(member.getLastname());
        members.setAddress(member.getAddress());
        members.setMobile(member.getMobile());
        members.setJoinedBy(member.getJoinedBy());
        members.setJoiningYear(member.getJoiningYear());
        members.setStatus(member.getStatus());

        // 🔥 (relation )
        UserEntity user = members.getUser();

        if(user != null){
            user.setFirstName(member.getFirstname());
            user.setLastName(member.getLastname());
            user.setMobile(member.getMobile());
        }

        return regMembersRepository.save(members);
    }
}