package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "admins",uniqueConstraints = @UniqueConstraint(columnNames = "userId"))
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    private String role ;
}