package com.trust.amanat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "super_admins")
@Data
public class SuperAdminEntity {
    @Id
    @GeneratedValue     (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
