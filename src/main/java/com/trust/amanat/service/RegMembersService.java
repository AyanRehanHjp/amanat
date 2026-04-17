package com.trust.amanat.service;

import com.trust.amanat.dto.PostHolderDTO;
import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.entity.PostHolderEntity;
import com.trust.amanat.entity.UserEntity;

import java.util.List;

public interface RegMembersService {
    public List <MembersEntity> getAllMembers();
    public MembersEntity addMember(MembersEntity member);

}
