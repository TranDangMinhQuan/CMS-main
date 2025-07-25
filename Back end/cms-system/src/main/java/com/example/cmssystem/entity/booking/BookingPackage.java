package com.example.cmssystem.entity.booking;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;

@Entity
@Table(name = "Booking_Package")
@Data
public class BookingPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Duration_Minutes", nullable = false)
    private Integer durationMinutes; // số phút trong gói (ví dụ 60, 90, 120)

    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "Description")
    @Nationalized
    private String description;

    @Column(name = "Is_Active")
    private Boolean isActive = true;
}
