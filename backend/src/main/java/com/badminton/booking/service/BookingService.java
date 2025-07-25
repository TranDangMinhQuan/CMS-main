package com.badminton.booking.service;

import com.badminton.booking.entity.Booking;
import com.badminton.booking.dto.BookingCreateDTO;
import com.badminton.booking.dto.BookingUpdateDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(BookingCreateDTO bookingCreateDTO, Long userId);
    Booking updateBooking(Long id, BookingUpdateDTO bookingUpdateDTO);
    Booking updateBookingStatus(Long id, Booking.BookingStatus status);
    
    Optional<Booking> getBookingById(Long id);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByUserId(Long userId);
    List<Booking> getBookingsByCourtId(Long courtId);
    List<Booking> getBookingsByStatus(Booking.BookingStatus status);
    List<Booking> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Booking> getBookingsByDate(LocalDateTime date);
    
    boolean isCourtAvailable(Long courtId, LocalDateTime startTime, LocalDateTime endTime);
    Double getMonthlyRevenue(int year, int month);
    void cancelBooking(Long id);
}