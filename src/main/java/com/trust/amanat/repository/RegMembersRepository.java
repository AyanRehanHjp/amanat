package com.trust.amanat.repository;

import com.trust.amanat.entity.MembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegMembersRepository extends JpaRepository <MembersEntity, Long> {
    MembersEntity findTopByOrderByIdDesc();
    MembersEntity findByMemberId(String memberId);
    boolean existsByMemberId(String memberId);

//    Searching by name
    @Query("SELECT m FROM MembersEntity m " +
            "WHERE LOWER(m.firstname) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(m.lastname) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<MembersEntity> searchMemByName(@Param("name") String name);

    @Query("SELECT m FROM MembersEntity m " +
            "WHERE m.mobile LIKE CONCAT('%', :mobile, '%')")
    List<MembersEntity> searchByMobile(@Param("mobile") String mobile);
}
