package com.trust.amanat.repository;

import com.trust.amanat.entity.IncomeDetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeDetRepository extends JpaRepository <IncomeDetEntity, Long> {
    @Query(value = "SELECT m.member_id, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'January' THEN i.amount END),0) AS Jan, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'February' THEN i.amount END),0) AS Feb, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'March' THEN i.amount END),0) AS Mar, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'April' THEN i.amount END),0) AS Apr, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'May' THEN i.amount END),0) AS May, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'June' THEN i.amount END),0) AS Jun, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'July' THEN i.amount END),0) AS Jul, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'August' THEN i.amount END),0) AS Aug, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'September' THEN i.amount END),0) AS Sep, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'October' THEN i.amount END),0) AS Oct, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'November' THEN i.amount END),0) AS Nov, " +
            "IFNULL(SUM(CASE WHEN i.for_month = 'December' THEN i.amount END),0) AS `Dec` " +
            "FROM members m " +
            "LEFT JOIN income_details i ON m.member_id = i.member_id AND i.for_year = :year " +
            "GROUP BY m.member_id",
            nativeQuery = true)
    List<Object[]> getMonthlyReportByYear(@Param("year") int year);
}
