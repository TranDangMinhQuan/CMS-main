package com.example.cmssystem.dto.court;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourtPackageDTO {
    private Long id;
    private String name; // VD: "Gói 1 Giờ", "Gói 1 Tháng"
    private int durationMinutes;
    private BigDecimal price;
    private String description;
    private Long courtId;
}
