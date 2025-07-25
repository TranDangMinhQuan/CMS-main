package com.badminton.booking.service;

import com.badminton.booking.entity.Coupon;

import java.math.BigDecimal;
import java.util.List;

public interface CouponService {
    List<Coupon> getAllCoupons();
    Coupon getCouponById(Long id);
    Coupon createCoupon(Coupon coupon);
    Coupon updateCoupon(Long id, Coupon coupon);
    void deleteCoupon(Long id);
    Coupon validateCoupon(String code);
    BigDecimal applyCoupon(String code, BigDecimal originalAmount);
    void useCoupon(String code);
}