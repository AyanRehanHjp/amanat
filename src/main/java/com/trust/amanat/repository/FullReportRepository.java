package com.trust.amanat.repository;

import com.trust.amanat.entity.FullReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FullReportRepository extends JpaRepository <FullReportEntity, Long> {
}
