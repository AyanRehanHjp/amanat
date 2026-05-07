package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "super_admins")
@Data
public class SuperAdminEntity {
    @Id
    @GeneratedValue     (strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

}
