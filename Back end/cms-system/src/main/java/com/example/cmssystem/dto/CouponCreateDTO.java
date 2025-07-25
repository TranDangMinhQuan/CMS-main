package com.example.cmssystem.dto;

import com.example.cmssystem.enums.CouponType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponCreateDTO {
    
    @NotBlank(message = "Coupon code is required")
    @Size(min = 3, max = 20, message = "Coupon code must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Z0-9_-]+$", message = "Coupon code can only contain uppercase letters, numbers, underscore and hyphen")
    private String code;
    
    @NotBlank(message = "Coupon name is required")
    @Size(min = 3, max = 100, message = "Coupon name must be between 3 and 100 characters")
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    @NotNull(message = "Coupon type is required")
    private CouponType type;
    
    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.01", message = "Discount value must be greater than 0")
    @DecimalMax(value = "99999.99", message = "Discount value is too large")
    private BigDecimal value;
    
    @DecimalMin(value = "0.00", message = "Minimum order amount cannot be negative")
    private BigDecimal minimumOrderAmount;
    
    @DecimalMin(value = "0.01", message = "Maximum discount amount must be greater than 0")
    private BigDecimal maximumDiscountAmount;
    
    @NotNull(message = "Valid from date is required")
    @FutureOrPresent(message = "Valid from date cannot be in the past")
    private LocalDate validFrom;
    
    @NotNull(message = "Valid to date is required")
    @Future(message = "Valid to date must be in the future")
    private LocalDate validTo;
    
    @NotNull(message = "Usage limit is required")
    @Min(value = 1, message = "Usage limit must be at least 1")
    @Max(value = 100000, message = "Usage limit is too large")
    private Integer usageLimit;
    
    @NotNull(message = "Usage limit per user is required")
    @Min(value = 1, message = "Usage limit per user must be at least 1")
    @Max(value = 1000, message = "Usage limit per user is too large")
    private Integer usageLimitPerUser;
}