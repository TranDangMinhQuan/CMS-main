package com.example.cmssystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CourtDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String district;
    private String city;
    private BigDecimal pricePerHour;
    private String imageUrl;
    private BigDecimal rating;
    private Integer totalReviews;
    private String phone;
    private List<String> facilities;
    private String openingTime;
    private String closingTime;
    private Boolean isActive;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> availableSlots; // For display purposes
    private String distance; // For search results
}