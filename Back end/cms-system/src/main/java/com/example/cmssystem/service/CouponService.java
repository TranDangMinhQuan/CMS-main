package com.example.cmssystem.service;

import com.example.cmssystem.dto.CouponCreateDTO;
import com.example.cmssystem.dto.CouponDTO;
import com.example.cmssystem.dto.CouponValidationDTO;
import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.entity.booking.Booking;
import com.example.cmssystem.entity.coupon.Coupon;
import com.example.cmssystem.entity.coupon.CouponUsage;
import com.example.cmssystem.enums.CouponStatus;
import com.example.cmssystem.enums.CouponType;
import com.example.cmssystem.repository.auth.AccountRepository;
import com.example.cmssystem.repository.CouponRepository;
import com.example.cmssystem.repository.CouponUsageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {
    
    private final CouponRepository couponRepository;
    private final CouponUsageRepository couponUsageRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    
    public List<CouponDTO> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Page<CouponDTO> getAllCoupons(Pageable pageable) {
        return couponRepository.findAll(pageable)
                .map(this::convertToDTO);
    }
    
    public Optional<CouponDTO> getCouponById(Long id) {
        return couponRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public Optional<CouponDTO> getCouponByCode(String code) {
        return couponRepository.findByCode(code.toUpperCase())
                .map(this::convertToDTO);
    }
    
    public List<CouponDTO> getValidCoupons() {
        return couponRepository.findValidCoupons(LocalDate.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CouponDTO createCoupon(CouponCreateDTO createDTO) {
        // Check if coupon code already exists
        if (couponRepository.existsByCode(createDTO.getCode().toUpperCase())) {
            throw new RuntimeException("Coupon code already exists");
        }
        
        // Validate date range
        if (createDTO.getValidTo().isBefore(createDTO.getValidFrom())) {
            throw new RuntimeException("Valid to date must be after valid from date");
        }
        
        // Validate percentage type constraints
        if (createDTO.getType() == CouponType.PERCENTAGE) {
            if (createDTO.getValue().compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new RuntimeException("Percentage discount cannot exceed 100%");
            }
        }
        
        // Validate usage limits
        if (createDTO.getUsageLimitPerUser() > createDTO.getUsageLimit()) {
            throw new RuntimeException("Usage limit per user cannot exceed total usage limit");
        }
        
        Coupon coupon = modelMapper.map(createDTO, Coupon.class);
        coupon.setCode(createDTO.getCode().toUpperCase());
        
        Coupon savedCoupon = couponRepository.save(coupon);
        return convertToDTO(savedCoupon);
    }
    
    public CouponDTO updateCoupon(Long id, CouponCreateDTO updateDTO) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        
        // Check if updating code and if new code already exists
        if (!coupon.getCode().equals(updateDTO.getCode().toUpperCase()) && 
            couponRepository.existsByCode(updateDTO.getCode().toUpperCase())) {
            throw new RuntimeException("Coupon code already exists");
        }
        
        // Update fields
        modelMapper.map(updateDTO, coupon);
        coupon.setCode(updateDTO.getCode().toUpperCase());
        
        Coupon savedCoupon = couponRepository.save(coupon);
        return convertToDTO(savedCoupon);
    }
    
    public void deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        
        // Check if coupon has been used
        if (coupon.getCurrentUsageCount() > 0) {
            throw new RuntimeException("Cannot delete coupon that has been used");
        }
        
        couponRepository.delete(coupon);
    }
    
    public CouponDTO activateCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        
        coupon.setIsActive(true);
        coupon.setStatus(CouponStatus.ACTIVE);
        
        Coupon savedCoupon = couponRepository.save(coupon);
        return convertToDTO(savedCoupon);
    }
    
    public CouponDTO deactivateCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        
        coupon.setIsActive(false);
        coupon.setStatus(CouponStatus.INACTIVE);
        
        Coupon savedCoupon = couponRepository.save(coupon);
        return convertToDTO(savedCoupon);
    }
    
    public CouponValidationDTO validateCoupon(String code, BigDecimal bookingAmount, Long userId) {
        Optional<Coupon> couponOpt = couponRepository.findValidCouponByCode(code.toUpperCase(), LocalDate.now());
        
        if (couponOpt.isEmpty()) {
            return CouponValidationDTO.invalid("Coupon code is invalid or expired");
        }
        
        Coupon coupon = couponOpt.get();
        
        // Check user usage limit
        if (userId != null) {
            int userUsageCount = couponRepository.countUsageByUserAndCoupon(coupon.getId(), userId);
            if (userUsageCount >= coupon.getUsageLimitPerUser()) {
                return CouponValidationDTO.invalid("You have reached the usage limit for this coupon");
            }
        }
        
        // Check minimum order amount
        if (coupon.getMinimumOrderAmount() != null && 
            bookingAmount.compareTo(coupon.getMinimumOrderAmount()) < 0) {
            return CouponValidationDTO.invalid(
                String.format("Minimum booking amount of $%.2f is required", 
                            coupon.getMinimumOrderAmount()));
        }
        
        // Calculate discount
        BigDecimal discountAmount = coupon.calculateDiscountAmount(bookingAmount);
        BigDecimal finalAmount = bookingAmount.subtract(discountAmount);
        
        return CouponValidationDTO.valid(convertToDTO(coupon), discountAmount, bookingAmount, finalAmount);
    }
    
    @Transactional
    public CouponUsage applyCoupon(String code, Booking booking, Account user) {
        CouponValidationDTO validation = validateCoupon(code, booking.getTotalAmount(), user.getId());
        
        if (!validation.isValid()) {
            throw new RuntimeException(validation.getMessage());
        }
        
        Coupon coupon = couponRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
        
        // Create coupon usage record
        CouponUsage usage = new CouponUsage();
        usage.setCoupon(coupon);
        usage.setUser(user);
        usage.setBooking(booking);
        usage.setDiscountAmount(validation.getDiscountAmount());
        usage.setOriginalAmount(validation.getOriginalAmount());
        usage.setFinalAmount(validation.getFinalAmount());
        
        // Update coupon usage count
        coupon.setCurrentUsageCount(coupon.getCurrentUsageCount() + 1);
        
        // Check if coupon is exhausted
        if (coupon.getCurrentUsageCount() >= coupon.getUsageLimit()) {
            coupon.setStatus(CouponStatus.EXHAUSTED);
        }
        
        couponRepository.save(coupon);
        return couponUsageRepository.save(usage);
    }
    
    public Page<CouponDTO> searchCoupons(String query, Pageable pageable) {
        return couponRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
                query, query, pageable).map(this::convertToDTO);
    }
    
    @Transactional
    public void updateExpiredCoupons() {
        List<Coupon> expiredCoupons = couponRepository.findExpiredCoupons(LocalDate.now());
        for (Coupon coupon : expiredCoupons) {
            coupon.setStatus(CouponStatus.EXPIRED);
        }
        couponRepository.saveAll(expiredCoupons);
    }
    
    @Transactional
    public void updateExhaustedCoupons() {
        List<Coupon> exhaustedCoupons = couponRepository.findExhaustedCoupons();
        for (Coupon coupon : exhaustedCoupons) {
            coupon.setStatus(CouponStatus.EXHAUSTED);
        }
        couponRepository.saveAll(exhaustedCoupons);
    }
    
    private CouponDTO convertToDTO(Coupon coupon) {
        CouponDTO dto = modelMapper.map(coupon, CouponDTO.class);
        
        // Set additional display fields
        dto.setIsValid(coupon.isValid());
        dto.setRemainingUsage(coupon.getUsageLimit() - coupon.getCurrentUsageCount());
        
        // Set display strings
        dto.setTypeDisplay(coupon.getType() == CouponType.PERCENTAGE ? 
                          coupon.getValue() + "% OFF" : 
                          "$" + coupon.getValue() + " OFF");
        
        dto.setStatusDisplay(getStatusDisplay(coupon.getStatus()));
        
        return dto;
    }
    
    private String getStatusDisplay(CouponStatus status) {
        return switch (status) {
            case ACTIVE -> "Active";
            case INACTIVE -> "Inactive";
            case EXPIRED -> "Expired";
            case EXHAUSTED -> "Exhausted";
        };
    }
}