package com.example.cmssystem.controller;

import com.example.cmssystem.dto.CouponDTO;
import com.example.cmssystem.dto.CouponRequestDTO;
import com.example.cmssystem.dto.CouponResponseDTO;
import com.example.cmssystem.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Coupon Management", description = "APIs for managing discount coupons")
public class CouponController {
    
    private final CouponService couponService;
    
    // CRUD Operations
    @Operation(summary = "Create a new coupon")
    @PostMapping
    public ResponseEntity<CouponResponseDTO> createCoupon(@Valid @RequestBody CouponRequestDTO request) {
        CouponResponseDTO coupon = couponService.createCoupon(request);
        return new ResponseEntity<>(coupon, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Update an existing coupon")
    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> updateCoupon(
            @PathVariable Long id,
            @Valid @RequestBody CouponRequestDTO request) {
        CouponResponseDTO coupon = couponService.updateCoupon(id, request);
        return ResponseEntity.ok(coupon);
    }
    
    @Operation(summary = "Delete a coupon")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Get coupon by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getCouponById(@PathVariable Long id) {
        return couponService.getCouponById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Get coupon by code")
    @GetMapping("/code/{code}")
    public ResponseEntity<CouponResponseDTO> getCouponByCode(@PathVariable String code) {
        return couponService.getCouponByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // List Operations
    @Operation(summary = "Get all active coupons")
    @GetMapping
    public ResponseEntity<List<CouponResponseDTO>> getAllActiveCoupons() {
        List<CouponResponseDTO> coupons = couponService.getAllActiveCoupons();
        return ResponseEntity.ok(coupons);
    }
    
    @Operation(summary = "Get active coupons with pagination and sorting")
    @GetMapping("/paginated")
    public ResponseEntity<Page<CouponResponseDTO>> getAllActiveCouponsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Page<CouponResponseDTO> coupons = couponService.getAllActiveCoupons(page, size, sortBy, sortDir);
        return ResponseEntity.ok(coupons);
    }
    
    @Operation(summary = "Get all valid coupons (not expired and within usage limit)")
    @GetMapping("/valid")
    public ResponseEntity<List<CouponResponseDTO>> getValidCoupons() {
        List<CouponResponseDTO> coupons = couponService.getValidCoupons();
        return ResponseEntity.ok(coupons);
    }
    
    @Operation(summary = "Get valid coupons with pagination")
    @GetMapping("/valid/paginated")
    public ResponseEntity<Page<CouponResponseDTO>> getValidCouponsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Page<CouponResponseDTO> coupons = couponService.getValidCoupons(page, size, sortBy, sortDir);
        return ResponseEntity.ok(coupons);
    }
    
    // Search Operations
    @Operation(summary = "Search coupons by keyword")
    @GetMapping("/search")
    public ResponseEntity<Page<CouponResponseDTO>> searchCoupons(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<CouponResponseDTO> coupons = couponService.searchCoupons(keyword, page, size);
        return ResponseEntity.ok(coupons);
    }
    
    @Operation(summary = "Get coupons by discount type")
    @GetMapping("/discount-type/{discountType}")
    public ResponseEntity<List<CouponResponseDTO>> getCouponsByDiscountType(
            @PathVariable 
            @Parameter(description = "Discount type: PERCENTAGE or FIXED_AMOUNT") 
            String discountType) {
        
        List<CouponResponseDTO> coupons = couponService.getCouponsByDiscountType(discountType);
        return ResponseEntity.ok(coupons);
    }
    
    // Validation Operations
    @Operation(summary = "Check if coupon is valid")
    @GetMapping("/{code}/validate")
    public ResponseEntity<Map<String, Boolean>> validateCoupon(@PathVariable String code) {
        boolean isValid = couponService.isCouponValid(code);
        return ResponseEntity.ok(Map.of("isValid", isValid));
    }
    
    @Operation(summary = "Validate coupon for specific order amount")
    @PostMapping("/{code}/validate-for-order")
    public ResponseEntity<CouponResponseDTO> validateCouponForOrder(
            @PathVariable String code,
            @RequestParam BigDecimal orderAmount) {
        
        return couponService.validateCouponForUse(code, orderAmount)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
    
    @Operation(summary = "Calculate discount amount for order")
    @PostMapping("/{code}/calculate-discount")
    public ResponseEntity<Map<String, BigDecimal>> calculateDiscountAmount(
            @PathVariable String code,
            @RequestParam BigDecimal orderAmount) {
        
        BigDecimal discountAmount = couponService.calculateDiscountAmount(code, orderAmount);
        return ResponseEntity.ok(Map.of(
            "discountAmount", discountAmount,
            "finalAmount", orderAmount.subtract(discountAmount)
        ));
    }
    
    // Usage Operations
    @Operation(summary = "Use a coupon (increment usage count)")
    @PostMapping("/{code}/use")
    public ResponseEntity<CouponResponseDTO> useCoupon(@PathVariable String code) {
        CouponResponseDTO coupon = couponService.useCoupon(code);
        return ResponseEntity.ok(coupon);
    }
    
    @Operation(summary = "Increment usage count of a coupon")
    @PatchMapping("/{code}/increment-usage")
    public ResponseEntity<Void> incrementUsageCount(@PathVariable String code) {
        couponService.incrementUsageCount(code);
        return ResponseEntity.ok().build();
    }
    
    // Admin Operations
    @Operation(summary = "Get expired coupons")
    @GetMapping("/expired")
    public ResponseEntity<List<CouponResponseDTO>> getExpiredCoupons() {
        List<CouponResponseDTO> coupons = couponService.getExpiredCoupons();
        return ResponseEntity.ok(coupons);
    }
    
    @Operation(summary = "Get coupons that reached usage limit")
    @GetMapping("/usage-limit-reached")
    public ResponseEntity<List<CouponResponseDTO>> getUsageLimitReachedCoupons() {
        List<CouponResponseDTO> coupons = couponService.getUsageLimitReachedCoupons();
        return ResponseEntity.ok(coupons);
    }
    
    @Operation(summary = "Activate a coupon")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateCoupon(@PathVariable Long id) {
        couponService.activateCoupon(id);
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "Deactivate a coupon")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCoupon(@PathVariable Long id) {
        couponService.deactivateCoupon(id);
        return ResponseEntity.ok().build();
    }
    
    // Statistics
    @Operation(summary = "Get coupon statistics")
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getCouponStatistics() {
        Map<String, Long> statistics = Map.of(
            "totalActiveCoupons", couponService.getTotalActiveCoupons(),
            "totalValidCoupons", couponService.getTotalValidCoupons(),
            "totalUsedCoupons", couponService.getTotalUsedCoupons()
        );
        return ResponseEntity.ok(statistics);
    }
}