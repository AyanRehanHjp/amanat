package com.trust.amanat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({"id","firstName","lastName","userName","gender","email","mobile",
        "city","state","pinCode","country","dateOfJoining","joinedBy","role"})
@Entity
@Getter
@Setter
@Table(name = "USER_SIGNUP_DETAILS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile", unique = true)
    private String mobile;

    @Column(name = "user_name", unique = true)
    private String userName;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "date_of_joining")
    private String dateOfJoining;

    @Column(name = "joined_by")
    private String joinedBy;

    @Column(name = "profile_image")
    private String profilePicture;

    @Column(name = "status")
    private String status;

    @Column(name = "approval_flag")
    private String approvalFlag;

    @Column(name = "member_id", unique = true, insertable = false, updatable = false)
    private String memberId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private MembersEntity member;

}