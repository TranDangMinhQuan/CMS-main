package com.example.cmssystem.dto;

import com.example.cmssystem.enums.CouponStatus;
import com.example.cmssystem.enums.CouponType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private CouponType type;
    private BigDecimal value;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscountAmount;
    private LocalDate validFrom;
    private LocalDate validTo;
    private Integer usageLimit;
    private Integer usageLimitPerUser;
    private Integer currentUsageCount;
    private Boolean isActive;
    private CouponStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Additional fields for frontend display
    private Boolean isValid;
    private Boolean canBeUsed;
    private Integer remainingUsage;
    private String typeDisplay;
    private String statusDisplay;
}