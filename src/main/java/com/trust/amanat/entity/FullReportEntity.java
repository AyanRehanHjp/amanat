package com.trust.amanat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "FULL_REPORT")
public class FullReportEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
}
