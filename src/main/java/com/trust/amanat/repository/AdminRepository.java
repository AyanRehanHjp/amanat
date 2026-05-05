package com.trust.amanat.repository;

import com.trust.amanat.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    boolean existsByUserId(String userId);

    AdminEntity findByUserId(String userId);
}