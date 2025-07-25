package com.example.cmssystem.repository;

import com.example.cmssystem.entity.coupon.Coupon;
import com.example.cmssystem.enums.CouponStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
    Optional<Coupon> findByCode(String code);
    
    boolean existsByCode(String code);
    
    List<Coupon> findByStatus(CouponStatus status);
    
    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.status = 'ACTIVE' " +
           "AND c.validFrom <= :currentDate AND c.validTo >= :currentDate " +
           "AND c.currentUsageCount < c.usageLimit")
    List<Coupon> findValidCoupons(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.status = 'ACTIVE' " +
           "AND c.validFrom <= :currentDate AND c.validTo >= :currentDate " +
           "AND c.currentUsageCount < c.usageLimit AND c.code = :code")
    Optional<Coupon> findValidCouponByCode(@Param("code") String code, @Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT c FROM Coupon c WHERE c.validTo < :currentDate AND c.status = 'ACTIVE'")
    List<Coupon> findExpiredCoupons(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT c FROM Coupon c WHERE c.currentUsageCount >= c.usageLimit AND c.status = 'ACTIVE'")
    List<Coupon> findExhaustedCoupons();
    
    Page<Coupon> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
            String name, String code, Pageable pageable);
    
    @Query("SELECT COUNT(cu) FROM CouponUsage cu WHERE cu.coupon.id = :couponId AND cu.user.id = :userId")
    int countUsageByUserAndCoupon(@Param("couponId") Long couponId, @Param("userId") Long userId);
}