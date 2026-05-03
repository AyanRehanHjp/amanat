package com.trust.amanat.repository;

import com.trust.amanat.entity.BeneficiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<BeneficiaryEntity, Long> {

    BeneficiaryEntity findByTokenId(String tokenId);
}
