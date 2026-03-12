package com.trust.amanat.repository;

import com.trust.amanat.entity.RecPdfGenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecPdfGenRepository extends JpaRepository <RecPdfGenEntity, Long> {
    Optional<RecPdfGenEntity> findByReceiptNo(String receiptNo);

}
