package com.trust.amanat.service;

import com.trust.amanat.entity.MembersEntity;
import org.springframework.data.domain.Page;

public interface RegMembersService {
    public Page<MembersEntity> getAllMembers(int page, int size);
    public MembersEntity addMember(MembersEntity member);
    public MembersEntity updateMember(String memberId, MembersEntity member);

}
