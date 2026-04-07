package com.trust.amanat.repository;

import com.trust.amanat.entity.IncomeDetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeDetRepository extends JpaRepository <IncomeDetEntity, Long> {
    boolean existsByMemberId(String memberId);
}
