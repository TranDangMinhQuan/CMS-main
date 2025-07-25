package com.example.cmssystem.service;

import com.example.cmssystem.dto.CouponDTO;
import com.example.cmssystem.dto.CouponRequestDTO;
import com.example.cmssystem.dto.CouponResponseDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CouponService {
    
    // CRUD operations
    CouponResponseDTO createCoupon(CouponRequestDTO request);
    
    CouponResponseDTO updateCoupon(Long id, CouponRequestDTO request);
    
    void deleteCoupon(Long id);
    
    Optional<CouponResponseDTO> getCouponById(Long id);
    
    Optional<CouponResponseDTO> getCouponByCode(String code);
    
    // List operations
    List<CouponResponseDTO> getAllActiveCoupons();
    
    Page<CouponResponseDTO> getAllActiveCoupons(int page, int size, String sortBy, String sortDir);
    
    List<CouponResponseDTO> getValidCoupons();
    
    Page<CouponResponseDTO> getValidCoupons(int page, int size, String sortBy, String sortDir);
    
    // Search operations
    Page<CouponResponseDTO> searchCoupons(String keyword, int page, int size);
    
    List<CouponResponseDTO> getCouponsByDiscountType(String discountType);
    
    // Validation operations
    boolean isCouponValid(String code);
    
    Optional<CouponResponseDTO> validateCouponForUse(String code, BigDecimal orderAmount);
    
    BigDecimal calculateDiscountAmount(String code, BigDecimal orderAmount);
    
    // Usage operations
    CouponResponseDTO useCoupon(String code);
    
    void incrementUsageCount(String code);
    
    // Admin operations
    List<CouponResponseDTO> getExpiredCoupons();
    
    List<CouponResponseDTO> getUsageLimitReachedCoupons();
    
    void activateCoupon(Long id);
    
    void deactivateCoupon(Long id);
    
    // Statistics
    long getTotalActiveCoupons();
    
    long getTotalValidCoupons();
    
    long getTotalUsedCoupons();
}