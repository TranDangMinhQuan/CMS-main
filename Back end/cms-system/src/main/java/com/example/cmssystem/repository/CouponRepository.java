package com.example.cmssystem.repository;

import com.example.cmssystem.entity.booking.Coupon;
import com.example.cmssystem.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
    // Find by code
    Optional<Coupon> findByCodeAndStatusAndIsActiveTrue(String code, Status status);
    
    Optional<Coupon> findByCode(String code);
    
    boolean existsByCode(String code);
    
    // Find active coupons
    List<Coupon> findByStatusAndIsActiveTrue(Status status);
    
    Page<Coupon> findByStatusAndIsActiveTrue(Status status, Pageable pageable);
    
    Optional<Coupon> findByIdAndStatusAndIsActiveTrue(Long id, Status status);
    
    // Find valid coupons (not expired and within usage limit)
    @Query("SELECT c FROM Coupon c WHERE c.status = :status AND c.isActive = true AND " +
           "c.startDate <= :currentTime AND c.endDate >= :currentTime AND " +
           "(c.usageLimit IS NULL OR c.usedCount < c.usageLimit)")
    List<Coupon> findValidCoupons(@Param("status") Status status, 
                                 @Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT c FROM Coupon c WHERE c.status = :status AND c.isActive = true AND " +
           "c.startDate <= :currentTime AND c.endDate >= :currentTime AND " +
           "(c.usageLimit IS NULL OR c.usedCount < c.usageLimit)")
    Page<Coupon> findValidCoupons(@Param("status") Status status, 
                                 @Param("currentTime") LocalDateTime currentTime, 
                                 Pageable pageable);
    
    // Search coupons
    @Query("SELECT c FROM Coupon c WHERE c.status = :status AND c.isActive = true AND " +
           "(c.code LIKE %:keyword% OR c.name LIKE %:keyword% OR c.description LIKE %:keyword%)")
    Page<Coupon> searchCoupons(@Param("status") Status status,
                              @Param("keyword") String keyword,
                              Pageable pageable);
    
    // Find coupons by discount type
    List<Coupon> findByDiscountTypeAndStatusAndIsActiveTrue(Coupon.DiscountType discountType, Status status);
    
    // Find expired coupons
    @Query("SELECT c FROM Coupon c WHERE c.status = :status AND c.isActive = true AND " +
           "c.endDate < :currentTime")
    List<Coupon> findExpiredCoupons(@Param("status") Status status, 
                                   @Param("currentTime") LocalDateTime currentTime);
    
    // Find coupons that reached usage limit
    @Query("SELECT c FROM Coupon c WHERE c.status = :status AND c.isActive = true AND " +
           "c.usageLimit IS NOT NULL AND c.usedCount >= c.usageLimit")
    List<Coupon> findUsageLimitReachedCoupons(@Param("status") Status status);
    
    // Check if coupon is valid for use
    @Query("SELECT c FROM Coupon c WHERE c.code = :code AND c.status = :status AND c.isActive = true AND " +
           "c.startDate <= :currentTime AND c.endDate >= :currentTime AND " +
           "(c.usageLimit IS NULL OR c.usedCount < c.usageLimit)")
    Optional<Coupon> findValidCouponByCode(@Param("code") String code,
                                          @Param("status") Status status,
                                          @Param("currentTime") LocalDateTime currentTime);
}