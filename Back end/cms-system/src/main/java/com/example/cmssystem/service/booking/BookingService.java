package com.example.cmssystem.service.booking;

import com.example.cmssystem.dto.booking.BookingRequestDTO;
import com.example.cmssystem.dto.booking.BookingResponseDTO;

import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO requestDTO);

    BookingResponseDTO getBookingById(Long id);

    List<BookingResponseDTO> getAllBookings();

    BookingResponseDTO checkInBooking(Long bookingId);

    void cancelBooking(Long bookingId);
}
