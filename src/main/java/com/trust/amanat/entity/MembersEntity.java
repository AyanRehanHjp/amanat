package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table (name = "members")
public class MembersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prefix;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "joining_year")
    private Integer joiningYear;

    private String mobile;

    private String address;

    @Column(name = "member_id")
    private String memberId;

    private String status;

    @Column(name = "joined_by")
    private String joinedBy;
}
