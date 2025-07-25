package com.badminton.booking.controller;

import com.badminton.booking.dto.BookingRequest;
import com.badminton.booking.entity.Booking;
import com.badminton.booking.enums.BookingStatus;
import com.badminton.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('MEMBER', 'STAFF', 'OWNER')")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest bookingRequest, 
                                               Authentication authentication) {
        Booking booking = bookingService.createBooking(bookingRequest, authentication.getName());
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("hasAnyRole('MEMBER', 'STAFF', 'OWNER')")
    public ResponseEntity<List<Booking>> getUserBookings(Authentication authentication) {
        List<Booking> bookings = bookingService.getUserBookings(authentication.getName());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEMBER', 'STAFF', 'OWNER')")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    @GetMapping("/today")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<List<Booking>> getTodayBookings() {
        return ResponseEntity.ok(bookingService.getTodayBookings());
    }

    @GetMapping("/monthly")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<List<Booking>> getMonthlyBookings(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(bookingService.getBookingsByMonth(month, year));
    }
}