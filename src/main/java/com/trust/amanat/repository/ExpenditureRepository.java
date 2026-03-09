package com.trust.amanat.repository;

import com.trust.amanat.entity.ExpenditureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenditureRepository extends JpaRepository <ExpenditureEntity, Long> {
}
