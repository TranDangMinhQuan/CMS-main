package com.badminton.booking.serviceimpl;

import com.badminton.booking.entity.Coupon;
import com.badminton.booking.repository.CouponRepository;
import com.badminton.booking.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(Long id, Coupon coupon) {
        Coupon existingCoupon = getCouponById(id);
        existingCoupon.setCode(coupon.getCode());
        existingCoupon.setDescription(coupon.getDescription());
        existingCoupon.setDiscountPercentage(coupon.getDiscountPercentage());
        existingCoupon.setMaxDiscountAmount(coupon.getMaxDiscountAmount());
        existingCoupon.setValidFrom(coupon.getValidFrom());
        existingCoupon.setValidUntil(coupon.getValidUntil());
        existingCoupon.setUsageLimit(coupon.getUsageLimit());
        existingCoupon.setActive(coupon.isActive());
        return couponRepository.save(existingCoupon);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public Coupon validateCoupon(String code) {
        return couponRepository.findValidCoupon(code, LocalDateTime.now())
                .orElseThrow(() -> new RuntimeException("Invalid or expired coupon"));
    }

    @Override
    public BigDecimal applyCoupon(String code, BigDecimal originalAmount) {
        Coupon coupon = validateCoupon(code);
        BigDecimal discountAmount = originalAmount.multiply(coupon.getDiscountPercentage().divide(BigDecimal.valueOf(100)));
        
        if (coupon.getMaxDiscountAmount() != null && discountAmount.compareTo(coupon.getMaxDiscountAmount()) > 0) {
            discountAmount = coupon.getMaxDiscountAmount();
        }
        
        return originalAmount.subtract(discountAmount);
    }

    @Override
    public void useCoupon(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.save(coupon);
    }
}