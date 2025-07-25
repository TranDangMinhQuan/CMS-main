package com.badminton.booking.service;

import com.badminton.booking.entity.RacketRental;

import java.math.BigDecimal;

public interface RacketRentalService {
    RacketRental createRacketRental(Long bookingId, Integer quantity);
    RacketRental getRacketRentalByBookingId(Long bookingId);
    BigDecimal getTodayRacketRevenue();
    BigDecimal getMonthlyRacketRevenue(int month, int year);
}