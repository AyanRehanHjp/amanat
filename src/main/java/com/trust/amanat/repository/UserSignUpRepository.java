package com.trust.amanat.repository;

import com.trust.amanat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSignUpRepository extends JpaRepository <UserEntity,Long> {
   boolean existsByUserName (String userName);
   boolean existsByEmail (String email);
   boolean existsByMobile (String mobile);

}

