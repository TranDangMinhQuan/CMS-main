package com.example.cmssystem.service.impl;

import com.example.cmssystem.dto.CouponDTO;
import com.example.cmssystem.dto.CouponRequestDTO;
import com.example.cmssystem.dto.CouponResponseDTO;
import com.example.cmssystem.entity.booking.Coupon;
import com.example.cmssystem.enums.Status;
import com.example.cmssystem.exception.exceptions.DuplicateException;
import com.example.cmssystem.exception.exceptions.NotFoundException;
import com.example.cmssystem.exception.exceptions.ValidationException;
import com.example.cmssystem.repository.CouponRepository;
import com.example.cmssystem.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CouponServiceImpl implements CouponService {
    
    private final CouponRepository couponRepository;
    private final ModelMapper modelMapper;
    
    @Override
    public CouponResponseDTO createCoupon(CouponRequestDTO request) {
        log.info("Creating coupon with code: {}", request.getCode());
        
        // Validate request
        validateCouponRequest(request);
        
        // Check if code already exists
        if (couponRepository.existsByCode(request.getCode())) {
            throw new DuplicateException("Coupon code already exists: " + request.getCode());
        }
        
        // Map to entity
        Coupon coupon = modelMapper.map(request, Coupon.class);
        coupon.setDiscountType(Coupon.DiscountType.valueOf(request.getDiscountType()));
        coupon.setStatus(Status.ACTIVE);
        coupon.setUsedCount(0);
        
        // Save
        Coupon savedCoupon = couponRepository.save(coupon);
        
        log.info("Coupon created successfully with ID: {}", savedCoupon.getId());
        return convertToResponseDTO(savedCoupon);
    }
    
    @Override
    public CouponResponseDTO updateCoupon(Long id, CouponRequestDTO request) {
        log.info("Updating coupon with ID: {}", id);
        
        Coupon existingCoupon = couponRepository.findByIdAndStatusAndIsActiveTrue(id, Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Coupon not found with ID: " + id));
        
        // Validate request
        validateCouponRequest(request);
        
        // Check if code is changed and already exists
        if (!existingCoupon.getCode().equals(request.getCode()) && 
            couponRepository.existsByCode(request.getCode())) {
            throw new DuplicateException("Coupon code already exists: " + request.getCode());
        }
        
        // Update fields
        existingCoupon.setCode(request.getCode());
        existingCoupon.setName(request.getName());
        existingCoupon.setDescription(request.getDescription());
        existingCoupon.setDiscountType(Coupon.DiscountType.valueOf(request.getDiscountType()));
        existingCoupon.setDiscountValue(request.getDiscountValue());
        existingCoupon.setMinOrderAmount(request.getMinOrderAmount());
        existingCoupon.setMaxDiscountAmount(request.getMaxDiscountAmount());
        existingCoupon.setUsageLimit(request.getUsageLimit());
        existingCoupon.setStartDate(request.getStartDate());
        existingCoupon.setEndDate(request.getEndDate());
        existingCoupon.setIsActive(request.getIsActive());
        
        Coupon updatedCoupon = couponRepository.save(existingCoupon);
        
        log.info("Coupon updated successfully with ID: {}", updatedCoupon.getId());
        return convertToResponseDTO(updatedCoupon);
    }
    
    @Override
    public void deleteCoupon(Long id) {
        log.info("Deleting coupon with ID: {}", id);
        
        Coupon coupon = couponRepository.findByIdAndStatusAndIsActiveTrue(id, Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Coupon not found with ID: " + id));
        
        coupon.setIsActive(false);
        coupon.setStatus(Status.INACTIVE);
        couponRepository.save(coupon);
        
        log.info("Coupon deleted successfully with ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<CouponResponseDTO> getCouponById(Long id) {
        return couponRepository.findByIdAndStatusAndIsActiveTrue(id, Status.ACTIVE)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<CouponResponseDTO> getCouponByCode(String code) {
        return couponRepository.findByCodeAndStatusAndIsActiveTrue(code, Status.ACTIVE)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CouponResponseDTO> getAllActiveCoupons() {
        List<Coupon> coupons = couponRepository.findByStatusAndIsActiveTrue(Status.ACTIVE);
        return coupons.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<CouponResponseDTO> getAllActiveCoupons(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? 
            Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Coupon> coupons = couponRepository.findByStatusAndIsActiveTrue(Status.ACTIVE, pageable);
        return coupons.map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CouponResponseDTO> getValidCoupons() {
        List<Coupon> coupons = couponRepository.findValidCoupons(Status.ACTIVE, LocalDateTime.now());
        return coupons.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<CouponResponseDTO> getValidCoupons(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? 
            Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Coupon> coupons = couponRepository.findValidCoupons(Status.ACTIVE, LocalDateTime.now(), pageable);
        return coupons.map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<CouponResponseDTO> searchCoupons(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Coupon> coupons = couponRepository.searchCoupons(Status.ACTIVE, keyword, pageable);
        return coupons.map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CouponResponseDTO> getCouponsByDiscountType(String discountType) {
        Coupon.DiscountType type = Coupon.DiscountType.valueOf(discountType);
        List<Coupon> coupons = couponRepository.findByDiscountTypeAndStatusAndIsActiveTrue(type, Status.ACTIVE);
        return coupons.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isCouponValid(String code) {
        return couponRepository.findValidCouponByCode(code, Status.ACTIVE, LocalDateTime.now()).isPresent();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<CouponResponseDTO> validateCouponForUse(String code, BigDecimal orderAmount) {
        Optional<Coupon> couponOpt = couponRepository.findValidCouponByCode(code, Status.ACTIVE, LocalDateTime.now());
        
        if (couponOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Coupon coupon = couponOpt.get();
        
        // Check minimum order amount
        if (coupon.getMinOrderAmount() != null && orderAmount.compareTo(coupon.getMinOrderAmount()) < 0) {
            return Optional.empty();
        }
        
        return Optional.of(convertToResponseDTO(coupon));
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateDiscountAmount(String code, BigDecimal orderAmount) {
        Optional<Coupon> couponOpt = couponRepository.findValidCouponByCode(code, Status.ACTIVE, LocalDateTime.now());
        
        if (couponOpt.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        Coupon coupon = couponOpt.get();
        
        // Check minimum order amount
        if (coupon.getMinOrderAmount() != null && orderAmount.compareTo(coupon.getMinOrderAmount()) < 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal discountAmount;
        
        if (coupon.getDiscountType() == Coupon.DiscountType.PERCENTAGE) {
            discountAmount = orderAmount.multiply(coupon.getDiscountValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        } else {
            discountAmount = coupon.getDiscountValue();
        }
        
        // Apply maximum discount limit
        if (coupon.getMaxDiscountAmount() != null && discountAmount.compareTo(coupon.getMaxDiscountAmount()) > 0) {
            discountAmount = coupon.getMaxDiscountAmount();
        }
        
        // Discount cannot exceed order amount
        if (discountAmount.compareTo(orderAmount) > 0) {
            discountAmount = orderAmount;
        }
        
        return discountAmount;
    }
    
    @Override
    public CouponResponseDTO useCoupon(String code) {
        Coupon coupon = couponRepository.findValidCouponByCode(code, Status.ACTIVE, LocalDateTime.now())
                .orElseThrow(() -> new ValidationException("Invalid or expired coupon: " + code));
        
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        Coupon updatedCoupon = couponRepository.save(coupon);
        
        log.info("Coupon used: {} (Usage: {}/{})", code, updatedCoupon.getUsedCount(), updatedCoupon.getUsageLimit());
        return convertToResponseDTO(updatedCoupon);
    }
    
    @Override
    public void incrementUsageCount(String code) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Coupon not found: " + code));
        
        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponRepository.save(coupon);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CouponResponseDTO> getExpiredCoupons() {
        List<Coupon> coupons = couponRepository.findExpiredCoupons(Status.ACTIVE, LocalDateTime.now());
        return coupons.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CouponResponseDTO> getUsageLimitReachedCoupons() {
        List<Coupon> coupons = couponRepository.findUsageLimitReachedCoupons(Status.ACTIVE);
        return coupons.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void activateCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coupon not found with ID: " + id));
        
        coupon.setIsActive(true);
        coupon.setStatus(Status.ACTIVE);
        couponRepository.save(coupon);
        
        log.info("Coupon activated with ID: {}", id);
    }
    
    @Override
    public void deactivateCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coupon not found with ID: " + id));
        
        coupon.setIsActive(false);
        coupon.setStatus(Status.INACTIVE);
        couponRepository.save(coupon);
        
        log.info("Coupon deactivated with ID: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalActiveCoupons() {
        return couponRepository.findByStatusAndIsActiveTrue(Status.ACTIVE).size();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalValidCoupons() {
        return couponRepository.findValidCoupons(Status.ACTIVE, LocalDateTime.now()).size();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalUsedCoupons() {
        return couponRepository.findByStatusAndIsActiveTrue(Status.ACTIVE).stream()
                .mapToLong(coupon -> coupon.getUsedCount() != null ? coupon.getUsedCount() : 0)
                .sum();
    }
    
    private void validateCouponRequest(CouponRequestDTO request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new ValidationException("End date must be after start date");
        }
        
        if (request.getDiscountType().equals("PERCENTAGE")) {
            if (request.getDiscountValue().compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new ValidationException("Percentage discount cannot exceed 100%");
            }
        }
    }
    
    private CouponResponseDTO convertToResponseDTO(Coupon coupon) {
        CouponResponseDTO dto = modelMapper.map(coupon, CouponResponseDTO.class);
        
        // Set computed fields
        LocalDateTime now = LocalDateTime.now();
        dto.setIsExpired(coupon.getEndDate().isBefore(now));
        
        boolean isUsageLimitReached = coupon.getUsageLimit() != null && 
                                     coupon.getUsedCount() >= coupon.getUsageLimit();
        dto.setIsUsageLimitReached(isUsageLimitReached);
        
        if (coupon.getUsageLimit() != null) {
            dto.setRemainingUsage(Math.max(0, coupon.getUsageLimit() - coupon.getUsedCount()));
        }
        
        // Format discount value
        if (coupon.getDiscountType() == Coupon.DiscountType.PERCENTAGE) {
            dto.setFormattedDiscountValue(coupon.getDiscountValue() + "%");
        } else {
            dto.setFormattedDiscountValue(coupon.getDiscountValue() + " VND");
        }
        
        // Set status and discount type as string
        dto.setStatus(coupon.getStatus().name());
        dto.setDiscountType(coupon.getDiscountType().name());
        
        return dto;
    }
}