package com.example.cmssystem.entity.court;

import com.example.cmssystem.entity.common.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "Court_Package")
@Data
public class CourtPackage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Court_Id", nullable = false)
    private Court court;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Duration_Minutes", nullable = false)
    private int durationMinutes;

    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "Description")
    private String description;
}
