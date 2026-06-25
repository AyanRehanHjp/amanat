package com.trust.amanat.serviceImpl;

import com.trust.amanat.common.constants.AppConstants;
import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.entity.UserEntity;
import com.trust.amanat.repository.RegMembersRepository;
import com.trust.amanat.repository.UserSignUpRepository;
import com.trust.amanat.service.RegMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegMembersServiceImpl implements RegMembersService {

    @Autowired
    RegMembersRepository regMembersRepository;

    @Autowired
    UserSignUpRepository userSignUpRepository;

    // ================= ADD MEMBER =================

    @Override
    @Transactional
    @CacheEvict(value = "membersCache", allEntries = true) // Evict cache after adding a member
    public MembersEntity addMember(MembersEntity member) {

        MembersEntity lastMember = regMembersRepository.findTopByOrderByIdDesc();

        String newMemberId = AppConstants.Message.DEFAULT_MEMBER_ID;

        if (lastMember != null && lastMember.getMemberId() != null) {
            String lastId = lastMember.getMemberId();
            String numberPart = lastId.replace(AppConstants.Message.AWT, "");
            int num = Integer.parseInt(numberPart);
            num++;
            newMemberId = String.format(AppConstants.Message.AWT_PLUS_MEM_ID_FORMAT , num);
        }

        MembersEntity newMember = new MembersEntity();
        newMember.setPrefix(member.getPrefix());
        newMember.setFirstname(member.getFirstname());
        newMember.setLastname(member.getLastname());
        newMember.setAddress(member.getAddress());
        if(userSignUpRepository.existsByMobile(member.getMobile())){
            throw new RuntimeException(AppConstants.Validation.MOBILE_NO_ALREADY_EXISTS );
        }
        newMember.setMobile(member.getMobile());
        newMember.setJoinedBy(member.getJoinedBy());
        newMember.setJoiningYear(member.getJoiningYear());
        newMember.setMemberId(newMemberId);
        newMember.setStatus(member.getStatus());
        newMember.setApprovalFlag(member.getApprovalFlag());

        // 🔥  save
        MembersEntity savedMember = regMembersRepository.save(newMember);

        // 🔥  user save
        UserEntity user = new UserEntity();
        user.setFirstName(savedMember.getFirstname());
        user.setLastName(savedMember.getLastname());
        user.setMobile(savedMember.getMobile());
        user.setApprovalFlag(savedMember.getApprovalFlag());
        user.setMember(savedMember);   // relation

        userSignUpRepository.save(user);
        return savedMember;
    }


    // ================= GET ALL =================
    @Override
    @Cacheable(value = "membersCache", key = "#page + '-' + #size") // Cache the result of this method
    public Page<MembersEntity> getAllMembers(int page, int size) {
         Pageable pageble = PageRequest.of(page, size);
        return regMembersRepository.findAll(pageble);
    }


    // ================= UPDATE MEMBER =================
    @Override
    @Transactional
    @CacheEvict(value = "membersCache", allEntries = true)
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
        members.setApprovalFlag(member.getApprovalFlag());


        // 🔥 (relation )
        UserEntity user = members.getUser();

        if(user != null){
            user.setFirstName(member.getFirstname());
            user.setLastName(member.getLastname());
            user.setMobile(member.getMobile());
            user.setJoinedBy(member.getJoinedBy());
            user.setCity(member.getAddress());
            user.setApprovalFlag(member.getApprovalFlag());
        }

        return regMembersRepository.save(members);
    }
    @Override
    public List <MembersEntity> searchMembersByName(String name) {
        return regMembersRepository.searchMemByName(name);
    }
    @Override
    public List <MembersEntity> searchByMobile(String mobile) {
        return regMembersRepository.searchByMobile(mobile);
    }
}