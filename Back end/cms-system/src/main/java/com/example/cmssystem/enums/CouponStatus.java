package com.example.cmssystem.enums;

public enum CouponStatus {
    ACTIVE,     // Coupon is active and can be used
    INACTIVE,   // Coupon is temporarily disabled
    EXPIRED,    // Coupon has expired
    EXHAUSTED   // Coupon has reached its usage limit
}