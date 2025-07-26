package com.example.cmssystem.dto.booking;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingPackageDTO {
    private Long id;
    private String name;
    private Integer durationMinutes;
    private BigDecimal price;
    private String description;
    private Boolean isActive;
}
