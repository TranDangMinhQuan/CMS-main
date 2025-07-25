package com.example.cmssystem.entity.coupon;

import com.example.cmssystem.enums.CouponStatus;
import com.example.cmssystem.enums.CouponType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 20)
    private String code;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type; // PERCENTAGE, FIXED_AMOUNT
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value; // Discount value (percentage or fixed amount)
    
    @Column(precision = 10, scale = 2)
    private BigDecimal minimumOrderAmount; // Minimum booking amount to use coupon
    
    @Column(precision = 10, scale = 2)
    private BigDecimal maximumDiscountAmount; // Maximum discount amount (for percentage coupons)
    
    @Column(nullable = false)
    private LocalDate validFrom;
    
    @Column(nullable = false)
    private LocalDate validTo;
    
    @Column(nullable = false)
    private Integer usageLimit; // Total usage limit
    
    @Column(nullable = false)
    private Integer usageLimitPerUser; // Usage limit per user
    
    @Column(nullable = false)
    private Integer currentUsageCount = 0; // Current total usage count
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status = CouponStatus.ACTIVE;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CouponUsage> couponUsages;
    
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (currentUsageCount == null) {
            currentUsageCount = 0;
        }
        if (isActive == null) {
            isActive = true;
        }
        if (status == null) {
            status = CouponStatus.ACTIVE;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper methods
    public boolean isValid() {
        LocalDate now = LocalDate.now();
        return isActive && 
               status == CouponStatus.ACTIVE &&
               !now.isBefore(validFrom) && 
               !now.isAfter(validTo) &&
               currentUsageCount < usageLimit;
    }
    
    public boolean canBeUsedByUser(Long userId, int userUsageCount) {
        return isValid() && userUsageCount < usageLimitPerUser;
    }
    
    public BigDecimal calculateDiscountAmount(BigDecimal bookingAmount) {
        if (minimumOrderAmount != null && bookingAmount.compareTo(minimumOrderAmount) < 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal discountAmount = BigDecimal.ZERO;
        
        switch (type) {
            case PERCENTAGE:
                discountAmount = bookingAmount.multiply(value).divide(BigDecimal.valueOf(100));
                if (maximumDiscountAmount != null && discountAmount.compareTo(maximumDiscountAmount) > 0) {
                    discountAmount = maximumDiscountAmount;
                }
                break;
            case FIXED_AMOUNT:
                discountAmount = value;
                if (discountAmount.compareTo(bookingAmount) > 0) {
                    discountAmount = bookingAmount;
                }
                break;
        }
        
        return discountAmount;
    }
}