package com.example.cmssystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal minOrderAmount;
    private BigDecimal maxDiscountAmount;
    private Integer usageLimit;
    private Integer usedCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Additional fields for business logic
    private Boolean isExpired;
    private Boolean isUsageLimitReached;
    private Integer remainingUsage;
}