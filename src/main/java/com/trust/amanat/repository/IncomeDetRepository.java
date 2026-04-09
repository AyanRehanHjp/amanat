package com.trust.amanat.repository;

import com.trust.amanat.entity.IncomeDetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeDetRepository extends JpaRepository <IncomeDetEntity, Long> {
    @Query(value = "SELECT member_id, " +
            "SUM(CASE WHEN for_month = 'January' THEN amount ELSE 0 END) AS Jan, " +
            "SUM(CASE WHEN for_month = 'February' THEN amount ELSE 0 END) AS Feb, " +
            "SUM(CASE WHEN for_month = 'March' THEN amount ELSE 0 END) AS Mar, " +
            "SUM(CASE WHEN for_month = 'April' THEN amount ELSE 0 END) AS Apr, " +
            "SUM(CASE WHEN for_month = 'May' THEN amount ELSE 0 END) AS May, " +
            "SUM(CASE WHEN for_month = 'June' THEN amount ELSE 0 END) AS Jun, " +
            "SUM(CASE WHEN for_month = 'July' THEN amount ELSE 0 END) AS Jul, " +
            "SUM(CASE WHEN for_month = 'August' THEN amount ELSE 0 END) AS Aug, " +
            "SUM(CASE WHEN for_month = 'September' THEN amount ELSE 0 END) AS Sep, " +
            "SUM(CASE WHEN for_month = 'October' THEN amount ELSE 0 END) AS Oct, " +
            "SUM(CASE WHEN for_month = 'November' THEN amount ELSE 0 END) AS Nov, " +
            "SUM(CASE WHEN for_month = 'December' THEN amount ELSE 0 END) AS `Dec` " +
            "FROM income_details WHERE for_year = :year GROUP BY member_id",
            nativeQuery = true)
    List<Object[]> getMonthlyReportByYear(@Param("year") int year);
}
