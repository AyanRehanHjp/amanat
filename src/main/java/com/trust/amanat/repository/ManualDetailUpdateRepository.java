package com.trust.amanat.repository;

import com.trust.amanat.entity.ManualDetailUpdateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManualDetailUpdateRepository extends JpaRepository<ManualDetailUpdateEntity, Long> {

}
