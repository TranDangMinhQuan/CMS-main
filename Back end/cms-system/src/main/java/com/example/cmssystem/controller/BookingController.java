package com.example.cmssystem.controller;

import com.example.cmssystem.dto.BookingRequestDTO;
import com.example.cmssystem.dto.BookingResponseDTO;
import com.example.cmssystem.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Booking Management", description = "APIs for managing court bookings")
public class BookingController {
    
    private final BookingService bookingService;
    
    @Operation(summary = "Create a new booking")
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingRequestDTO bookingRequest,
            Authentication authentication) {
        
        String userEmail = authentication != null ? authentication.getName() : null;
        
        try {
            BookingResponseDTO booking = bookingService.createBooking(bookingRequest, userEmail);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Create booking for guest (no authentication required)")
    @PostMapping("/guest")
    public ResponseEntity<BookingResponseDTO> createGuestBooking(
            @Valid @RequestBody BookingRequestDTO bookingRequest) {
        
        try {
            BookingResponseDTO booking = bookingService.createBooking(bookingRequest, null);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Get booking by booking code")
    @GetMapping("/code/{bookingCode}")
    public ResponseEntity<BookingResponseDTO> getBookingByCode(@PathVariable String bookingCode) {
        return bookingService.getBookingByCode(bookingCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Get bookings for current user")
    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingResponseDTO>> getMyBookings(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String userEmail = authentication.getName();
        List<BookingResponseDTO> bookings = bookingService.getBookingsByCustomer(userEmail);
        return ResponseEntity.ok(bookings);
    }
    
    @Operation(summary = "Get bookings for current user with pagination")
    @GetMapping("/my-bookings/paginated")
    public ResponseEntity<Page<BookingResponseDTO>> getMyBookingsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String userEmail = authentication.getName();
        Page<BookingResponseDTO> bookings = bookingService.getBookingsByCustomer(userEmail, page, size);
        return ResponseEntity.ok(bookings);
    }
    
    @Operation(summary = "Confirm a booking")
    @PutMapping("/{id}/confirm")
    public ResponseEntity<BookingResponseDTO> confirmBooking(@PathVariable Long id) {
        try {
            BookingResponseDTO booking = bookingService.confirmBooking(id);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Cancel a booking")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(
            @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        
        try {
            BookingResponseDTO booking = bookingService.cancelBooking(id, reason);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Get available time slots for a court on specific date")
    @GetMapping("/available-slots")
    public ResponseEntity<List<String>> getAvailableTimeSlots(
            @RequestParam Long courtId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        try {
            List<LocalTime[]> slots = bookingService.getAvailableTimeSlots(courtId, date);
            List<String> slotStrings = slots.stream()
                    .map(slot -> slot[0] + "-" + slot[1])
                    .toList();
            return ResponseEntity.ok(slotStrings);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}