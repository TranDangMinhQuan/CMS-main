package com.example.cmssystem.dto.court;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CourtResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String district;
    private String city;
    private String phone;
    private String imageUrl;

    private BigDecimal pricePerHour;
    private BigDecimal rating;
    private Integer totalReviews;

    private List<String> facilities;
    private String openingTime;
    private String closingTime;

    private Boolean isActive;
    private String status;

    private List<String> availableSlots; // Các khung giờ còn trống
    private String distance; // Hiển thị cho khách, dựa theo vị trí tìm kiếm
}
