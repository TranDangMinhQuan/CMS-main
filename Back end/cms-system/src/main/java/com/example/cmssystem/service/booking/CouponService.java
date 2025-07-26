package com.example.cmssystem.service.booking;

import com.example.cmssystem.dto.booking.CouponDTO;

import java.util.List;

public interface CouponService {
    CouponDTO create(CouponDTO dto);
    CouponDTO update(Long id, CouponDTO dto);
    void delete(Long id);
    CouponDTO getById(Long id);
    List<CouponDTO> getAllActive();
    CouponDTO validateCoupon(String code);
}
