package com.trust.amanat.repository;

import com.trust.amanat.entity.SuperAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdminEntity, Long> {

    boolean existsByUsername(String username);

    SuperAdminEntity findByUsername(String username);}
