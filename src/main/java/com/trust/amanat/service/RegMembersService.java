package com.trust.amanat.service;

import com.trust.amanat.dto.PostHolderDTO;
import com.trust.amanat.entity.MembersEntity;
import com.trust.amanat.entity.PostHolderEntity;
import com.trust.amanat.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RegMembersService {
    public Page<MembersEntity> getAllMembers(int page, int size);
    public MembersEntity addMember(MembersEntity member);
    public MembersEntity updateMember(String memberId, MembersEntity member);

}
