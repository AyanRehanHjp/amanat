package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "manual_detail_update")
@Data
public class ManualDetailUpdateEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

}
