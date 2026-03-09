package com.trust.amanat.repository;

import com.trust.amanat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSignInRepository extends JpaRepository <UserEntity, Long> {
    List<UserEntity> findByUserName (String userName);

}
