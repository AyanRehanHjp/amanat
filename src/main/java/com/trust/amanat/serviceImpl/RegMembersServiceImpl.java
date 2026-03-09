package com.trust.amanat.serviceImpl;

import com.trust.amanat.entity.MembersEntity;
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
    public List <MembersEntity> getAllMembers() {
        List <MembersEntity> members = regMembersRepository.findAll();
        return  members;
    }
}
