package com.trust.amanat.repository;

import com.trust.amanat.entity.ExpenditureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenditureRepository extends JpaRepository <ExpenditureEntity, Long> {
    @Query("SELECT SUM(e.amount) FROM ExpenditureEntity e")
    Double getTotalExpenditure();
    @Query("SELECT e.year, SUM(e.amount) FROM ExpenditureEntity e GROUP BY e.year")
    List<Object[]> getYearlyExpense();
}
