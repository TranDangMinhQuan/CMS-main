package com.example.cmssystem.controller;

import com.example.cmssystem.dto.CouponCreateDTO;
import com.example.cmssystem.dto.CouponDTO;
import com.example.cmssystem.dto.CouponValidationDTO;
import com.example.cmssystem.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Coupon Management", description = "APIs for managing discount coupons")
public class CouponController {
    
    private final CouponService couponService;
    
    @GetMapping
    @Operation(summary = "Get all coupons", description = "Retrieve all coupons with pagination")
    public ResponseEntity<Page<CouponDTO>> getAllCoupons(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                   Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<CouponDTO> coupons = couponService.getAllCoupons(pageable);
        return ResponseEntity.ok(coupons);
    }
    
    @GetMapping("/valid")
    @Operation(summary = "Get valid coupons", description = "Retrieve all currently valid and active coupons")
    public ResponseEntity<List<CouponDTO>> getValidCoupons() {
        List<CouponDTO> coupons = couponService.getValidCoupons();
        return ResponseEntity.ok(coupons);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get coupon by ID", description = "Retrieve a specific coupon by its ID")
    public ResponseEntity<CouponDTO> getCouponById(
            @Parameter(description = "Coupon ID") @PathVariable Long id) {
        
        Optional<CouponDTO> coupon = couponService.getCouponById(id);
        return coupon.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/code/{code}")
    @Operation(summary = "Get coupon by code", description = "Retrieve a specific coupon by its code")
    public ResponseEntity<CouponDTO> getCouponByCode(
            @Parameter(description = "Coupon code") @PathVariable String code) {
        
        Optional<CouponDTO> coupon = couponService.getCouponByCode(code);
        return coupon.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create coupon", description = "Create a new discount coupon (Admin only)")
    public ResponseEntity<CouponDTO> createCoupon(
            @Parameter(description = "Coupon creation data") @Valid @RequestBody CouponCreateDTO createDTO) {
        
        try {
            CouponDTO createdCoupon = couponService.createCoupon(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update coupon", description = "Update an existing coupon (Admin only)")
    public ResponseEntity<CouponDTO> updateCoupon(
            @Parameter(description = "Coupon ID") @PathVariable Long id,
            @Parameter(description = "Coupon update data") @Valid @RequestBody CouponCreateDTO updateDTO) {
        
        try {
            CouponDTO updatedCoupon = couponService.updateCoupon(id, updateDTO);
            return ResponseEntity.ok(updatedCoupon);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete coupon", description = "Delete a coupon (Admin only)")
    public ResponseEntity<Void> deleteCoupon(
            @Parameter(description = "Coupon ID") @PathVariable Long id) {
        
        try {
            couponService.deleteCoupon(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activate coupon", description = "Activate a coupon (Admin only)")
    public ResponseEntity<CouponDTO> activateCoupon(
            @Parameter(description = "Coupon ID") @PathVariable Long id) {
        
        try {
            CouponDTO activatedCoupon = couponService.activateCoupon(id);
            return ResponseEntity.ok(activatedCoupon);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate coupon", description = "Deactivate a coupon (Admin only)")
    public ResponseEntity<CouponDTO> deactivateCoupon(
            @Parameter(description = "Coupon ID") @PathVariable Long id) {
        
        try {
            CouponDTO deactivatedCoupon = couponService.deactivateCoupon(id);
            return ResponseEntity.ok(deactivatedCoupon);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/validate")
    @Operation(summary = "Validate coupon", description = "Validate a coupon code for a specific booking amount")
    public ResponseEntity<CouponValidationDTO> validateCoupon(
            @Parameter(description = "Coupon code") @RequestParam String code,
            @Parameter(description = "Booking amount") @RequestParam BigDecimal amount,
            @Parameter(description = "User ID (optional)") @RequestParam(required = false) Long userId) {
        
        CouponValidationDTO validation = couponService.validateCoupon(code, amount, userId);
        return ResponseEntity.ok(validation);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search coupons", description = "Search coupons by name or code")
    public ResponseEntity<Page<CouponDTO>> searchCoupons(
            @Parameter(description = "Search query") @RequestParam String query,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CouponDTO> coupons = couponService.searchCoupons(query, pageable);
        return ResponseEntity.ok(coupons);
    }
    
    @PostMapping("/update-status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update coupon statuses", description = "Update expired and exhausted coupon statuses (Admin only)")
    public ResponseEntity<String> updateCouponStatuses() {
        couponService.updateExpiredCoupons();
        couponService.updateExhaustedCoupons();
        return ResponseEntity.ok("Coupon statuses updated successfully");
    }
}