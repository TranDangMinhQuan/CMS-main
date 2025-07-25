package com.badminton.booking.serviceimpl;

import com.badminton.booking.entity.RacketRental;
import com.badminton.booking.repository.BookingRepository;
import com.badminton.booking.repository.RacketRentalRepository;
import com.badminton.booking.service.RacketRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RacketRentalServiceImpl implements RacketRentalService {

    @Autowired
    private RacketRentalRepository racketRentalRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public RacketRental createRacketRental(Long bookingId, Integer quantity) {
        RacketRental racketRental = new RacketRental();
        racketRental.setBooking(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found")));
        racketRental.setQuantity(quantity);
        return racketRentalRepository.save(racketRental);
    }

    @Override
    public RacketRental getRacketRentalByBookingId(Long bookingId) {
        return racketRentalRepository.findByBookingId(bookingId).orElse(null);
    }

    @Override
    public BigDecimal getTodayRacketRevenue() {
        BigDecimal revenue = racketRentalRepository.getTodayRacketRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getMonthlyRacketRevenue(int month, int year) {
        BigDecimal revenue = racketRentalRepository.getMonthlyRacketRevenue(month, year);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
}