package com.trust.amanat.repository;

import com.trust.amanat.entity.ScanPayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScanPayRepository extends JpaRepository <ScanPayEntity, Long> {
    @Query(value =
            "SELECT * FROM scan_pay_details WHERE UPPER(entry_status) = UPPER(:status)",
            nativeQuery = true)
    List<ScanPayEntity> findByStatus(@Param("status") String status);
}
