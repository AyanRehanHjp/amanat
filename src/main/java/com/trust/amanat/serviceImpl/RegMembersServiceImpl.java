package com.trust.amanat.serviceImpl;

import com.trust.amanat.dto.PostHolderDTO;
import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.entity.PostHolderEntity;
import com.trust.amanat.repository.RegMembersRepository;
import com.trust.amanat.service.RegMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RegMembersServiceImpl implements RegMembersService {


    @Autowired
    RegMembersRepository regMembersRepository;

    @Override
    public MembersEntity addMember(MembersEntity member) {
        MembersEntity lastMember = regMembersRepository.findTopByOrderByIdDesc();

        String newMemberId = "AWT001";

        if (lastMember != null && lastMember.getMemberId() != null) {

            String lastId = lastMember.getMemberId(); // AWT145

            // 🔥 prefix remove karo
            String numberPart = lastId.replace("AWT", ""); // 145

            int num = Integer.parseInt(numberPart);
            num++;

            newMemberId = "AWT" + num; // AWT146
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
        return regMembersRepository.save(newMember);
    }
    @Override
    public List <MembersEntity> getAllMembers() {
        List <MembersEntity> members = regMembersRepository.findAll();
        return  members;
    }



}
