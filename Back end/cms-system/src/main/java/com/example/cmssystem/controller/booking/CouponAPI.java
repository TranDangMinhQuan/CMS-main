package com.example.cmssystem.controller.booking;

import com.example.cmssystem.dto.booking.CouponDTO;
import com.example.cmssystem.service.booking.CouponService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
@SecurityRequirement(name = "api")
public class CouponAPI {

    private final CouponService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<CouponDTO> create(@RequestBody CouponDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<CouponDTO> update(@PathVariable Long id, @RequestBody CouponDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CouponDTO>> getAllActive() {
        return ResponseEntity.ok(service.getAllActive());
    }

    @GetMapping("/validate")
    public ResponseEntity<CouponDTO> validate(@RequestParam String code) {
        return ResponseEntity.ok(service.validateCoupon(code));
    }
}
