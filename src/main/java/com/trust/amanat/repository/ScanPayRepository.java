package com.trust.amanat.repository;

import com.trust.amanat.entity.ScanPayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanPayRepository extends JpaRepository <ScanPayEntity, Long> {
    
}
