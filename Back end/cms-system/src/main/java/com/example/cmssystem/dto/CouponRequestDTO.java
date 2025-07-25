package com.example.cmssystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponRequestDTO {
    
    @NotBlank(message = "Coupon code is required")
    @Size(min = 3, max = 20, message = "Coupon code must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Coupon code must contain only uppercase letters and numbers")
    private String code;

    @NotBlank(message = "Coupon name is required")
    @Size(max = 100, message = "Coupon name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Discount type is required")
    private String discountType; // PERCENTAGE, FIXED_AMOUNT

    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.01", message = "Discount value must be greater than 0")
    private BigDecimal discountValue;

    @DecimalMin(value = "0", message = "Minimum order amount must be greater than or equal to 0")
    private BigDecimal minOrderAmount;

    @DecimalMin(value = "0", message = "Maximum discount amount must be greater than or equal to 0")
    private BigDecimal maxDiscountAmount;

    @Min(value = 1, message = "Usage limit must be at least 1")
    private Integer usageLimit;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    private Boolean isActive = true;
}