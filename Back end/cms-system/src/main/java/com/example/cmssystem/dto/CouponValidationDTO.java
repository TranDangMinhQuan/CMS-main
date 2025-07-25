package com.example.cmssystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponValidationDTO {
    private boolean valid;
    private String message;
    private CouponDTO coupon;
    private BigDecimal discountAmount;
    private BigDecimal originalAmount;
    private BigDecimal finalAmount;
    
    public static CouponValidationDTO valid(CouponDTO coupon, BigDecimal discountAmount, 
                                           BigDecimal originalAmount, BigDecimal finalAmount) {
        return new CouponValidationDTO(true, "Coupon is valid", coupon, 
                                     discountAmount, originalAmount, finalAmount);
    }
    
    public static CouponValidationDTO invalid(String message) {
        return new CouponValidationDTO(false, message, null, 
                                     BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}