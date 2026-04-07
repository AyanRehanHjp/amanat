package com.trust.amanat.repository;

import com.trust.amanat.entity.PostHolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostHolderRepository extends JpaRepository <PostHolderEntity,Long> {
}
