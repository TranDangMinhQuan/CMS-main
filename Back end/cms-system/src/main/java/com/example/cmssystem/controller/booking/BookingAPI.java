package com.example.cmssystem.controller.booking;

import com.example.cmssystem.dto.booking.BookingRequestDTO;
import com.example.cmssystem.dto.booking.BookingResponseDTO;
import com.example.cmssystem.service.booking.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@SecurityRequirement(name = "api")
public class BookingAPI {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('MEMBER')")
    public BookingResponseDTO createBooking(@Valid @RequestBody BookingRequestDTO requestDTO) {
        return bookingService.createBooking(requestDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEMBER', 'STAFF', 'OWNER')")
    public BookingResponseDTO getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('OWNER')")
    public List<BookingResponseDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PutMapping("/{id}/check-in")
    @PreAuthorize("hasRole('STAFF')")
    public BookingResponseDTO checkIn(@PathVariable Long id) {
        return bookingService.checkInBooking(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER')")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }
}
