package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "admins")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String designation;
    private String userId;
    private String password;

}