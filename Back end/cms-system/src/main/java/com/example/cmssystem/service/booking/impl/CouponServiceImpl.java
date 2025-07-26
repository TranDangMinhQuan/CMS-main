package com.example.cmssystem.service.booking.impl;

import com.example.cmssystem.dto.booking.CouponDTO;
import com.example.cmssystem.entity.booking.Coupon;
import com.example.cmssystem.exception.exceptions.ResourceNotFoundException;
import com.example.cmssystem.repository.booking.CouponRepository;
import com.example.cmssystem.service.booking.CouponService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CouponDTO create(CouponDTO dto) {
        Coupon coupon = mapper.map(dto, Coupon.class);
        coupon.setUsedCount(0);
        return mapper.map(repository.save(coupon), CouponDTO.class);
    }

    @Override
    public CouponDTO update(Long id, CouponDTO dto) {
        Coupon existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));

        existing.setCode(dto.getCode());
        existing.setDiscountAmount(dto.getDiscountAmount());
        existing.setDiscountPercent(dto.getDiscountPercent());
        existing.setValidFrom(dto.getValidFrom());
        existing.setValidUntil(dto.getValidUntil());
        existing.setIsActive(dto.getIsActive());
        existing.setUsageLimit(dto.getUsageLimit());

        return mapper.map(repository.save(existing), CouponDTO.class);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CouponDTO getById(Long id) {
        return repository.findById(id)
                .map(entity -> mapper.map(entity, CouponDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));
    }

    @Override
    public List<CouponDTO> getAllActive() {
        return repository.findAll().stream()
                .filter(c -> Boolean.TRUE.equals(c.getIsActive()))
                .map(c -> mapper.map(c, CouponDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CouponDTO validateCoupon(String code) {
        Coupon coupon = repository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));

        if (!coupon.getIsActive()) {
            throw new IllegalArgumentException("Coupon is inactive.");
        }
        if (coupon.getValidFrom() != null && coupon.getValidFrom().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Coupon not yet valid.");
        }
        if (coupon.getValidUntil() != null && coupon.getValidUntil().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Coupon expired.");
        }
        if (coupon.getUsageLimit() != null && coupon.getUsedCount() >= coupon.getUsageLimit()) {
            throw new IllegalArgumentException("Coupon usage limit reached.");
        }

        return mapper.map(coupon, CouponDTO.class);
    }
}
