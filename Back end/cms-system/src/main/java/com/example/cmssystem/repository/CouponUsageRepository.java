package com.example.cmssystem.repository;

import com.example.cmssystem.entity.coupon.CouponUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {
    
    List<CouponUsage> findByUserId(Long userId);
    
    List<CouponUsage> findByCouponId(Long couponId);
    
    int countByUserIdAndCouponId(Long userId, Long couponId);
    
    @Query("SELECT cu FROM CouponUsage cu WHERE cu.user.id = :userId ORDER BY cu.usedAt DESC")
    Page<CouponUsage> findByUserIdOrderByUsedAtDesc(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT cu FROM CouponUsage cu WHERE cu.coupon.id = :couponId ORDER BY cu.usedAt DESC")
    Page<CouponUsage> findByCouponIdOrderByUsedAtDesc(@Param("couponId") Long couponId, Pageable pageable);
    
    boolean existsByBookingId(Long bookingId);
}