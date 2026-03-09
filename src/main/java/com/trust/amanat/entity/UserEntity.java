package com.trust.amanat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@JsonPropertyOrder({"id","firstName","lastName","userName","gender","email","mobile"
        ,"city","state","pinCode","country","dateOfJoining","joinedBy","role"})
@Entity
@Getter
@Setter

@Table (name = "TB_MEMBER_DETAILS")
public class UserEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;

    @Column (unique = true)
    private String mobile;

    @Column (unique = true)
    private String userName;

    @JsonIgnore
    private String password;
    private String role;

    @Column (unique = true)
    private String email;
    private String gender;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String dateOfJoining;
    private String joinedBy;
    @Column(name = "profile_image")
    private String profilePicture;



}
