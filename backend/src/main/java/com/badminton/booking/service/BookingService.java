package com.badminton.booking.service;

import com.badminton.booking.dto.BookingRequest;
import com.badminton.booking.entity.Booking;
import com.badminton.booking.enums.BookingStatus;

import java.util.List;

public interface BookingService {
    Booking createBooking(BookingRequest bookingRequest, String username);
    List<Booking> getUserBookings(String username);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    Booking updateBookingStatus(Long id, BookingStatus status);
    List<Booking> getTodayBookings();
    List<Booking> getBookingsByMonth(int month, int year);
    boolean isCourtAvailable(Long courtId, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime);
}