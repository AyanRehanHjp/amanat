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
            "CONCAT(m.first_name, ' ', m.last_name) AS name, " +
            "m.mobile, m.address, " +

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
            "GROUP BY m.member_id, m.first_name, m.last_name, m.mobile, m.address",

            nativeQuery = true)
    List<Object[]> getMonthlyReportByYear(@Param("year") int year);
    @Query(value = "SELECT m.member_id, " +
            "CONCAT(m.first_name, ' ', m.last_name) AS name, " +
            "m.mobile, m.address, " +

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
            "WHERE m.member_id = :memberId " +
            "GROUP BY m.member_id, m.first_name, m.last_name, m.mobile, m.address",

            nativeQuery = true)
    List<Object[]> getMonthlyReportByYearAndMember(@Param("year") int year,
                                                   @Param("memberId") String memberId);
    @Query(value = "SELECT member_id, CONCAT(first_name,' ',last_name) as name, mobile " +
            "FROM members " +
            "WHERE member_id LIKE %:value% " +
            "OR mobile LIKE %:value% " +
            "OR first_name LIKE %:value% " +
            "OR last_name LIKE %:value%",
            nativeQuery = true)
    List<Object[]> searchMember(@Param("value") String value);

    @Query("SELECT SUM(i.amount) FROM IncomeDetEntity i")
    Double getTotalIncome();

    @Query("SELECT i.forYear, SUM(i.amount) FROM IncomeDetEntity i GROUP BY i.forYear")
    List<Object[]> getYearlyIncome();

    boolean existsByMemberId(String memberId);
    IncomeDetEntity findTopByOrderByIdDesc();
}
