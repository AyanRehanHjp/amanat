package com.trust.amanat.repository;

import com.trust.amanat.entity.MembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegMembersRepository extends JpaRepository <MembersEntity, Long> {

}
