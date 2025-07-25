package com.example.cmssystem.service;

import com.example.cmssystem.dto.BookingRequestDTO;
import com.example.cmssystem.dto.BookingResponseDTO;
import com.example.cmssystem.entity.auth.Account;
import com.example.cmssystem.entity.booking.Booking;
import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.enums.BookingStatus;
import com.example.cmssystem.repository.BookingRepository;
import com.example.cmssystem.repository.CourtRepository;
import com.example.cmssystem.repository.auth.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final CourtRepository courtRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final CourtService courtService;
    
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO, String userEmail) {
        // Validate court exists
        Court court = courtRepository.findById(requestDTO.getCourtId())
                .orElseThrow(() -> new RuntimeException("Court not found"));
        
        // Validate time slot
        validateTimeSlot(court, requestDTO.getBookingDate(), 
                        requestDTO.getStartTime(), requestDTO.getEndTime());
        
        // Get customer
        Account customer = null;
        if (userEmail != null) {
            customer = accountRepository.findByEmail(userEmail).orElse(null);
        }
        
        // Calculate duration and amount
        Duration duration = Duration.between(requestDTO.getStartTime(), requestDTO.getEndTime());
        BigDecimal totalHours = BigDecimal.valueOf(duration.toMinutes()).divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalAmount = court.getPricePerHour().multiply(totalHours);
        
        // Create booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setCourt(court);
        booking.setBookingDate(requestDTO.getBookingDate());
        booking.setStartTime(requestDTO.getStartTime());
        booking.setEndTime(requestDTO.getEndTime());
        booking.setTotalHours(totalHours);
        booking.setPricePerHour(court.getPricePerHour());
        booking.setTotalAmount(totalAmount);
        booking.setFinalAmount(totalAmount);
        booking.setCustomerName(requestDTO.getCustomerName());
        booking.setCustomerPhone(requestDTO.getCustomerPhone());
        booking.setCustomerEmail(requestDTO.getCustomerEmail());
        booking.setNotes(requestDTO.getNotes());
        booking.setStatus(BookingStatus.PENDING);
        
        booking = bookingRepository.save(booking);
        
        return convertToResponseDTO(booking);
    }
    
    public Optional<BookingResponseDTO> getBookingByCode(String bookingCode) {
        Optional<Booking> booking = bookingRepository.findByBookingCode(bookingCode);
        return booking.map(this::convertToResponseDTO);
    }
    
    public List<BookingResponseDTO> getBookingsByCustomer(String userEmail) {
        Account customer = accountRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        List<Booking> bookings = bookingRepository.findByCustomerOrderByCreatedAtDesc(customer);
        return bookings.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public Page<BookingResponseDTO> getBookingsByCustomer(String userEmail, int page, int size) {
        Account customer = accountRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Booking> bookings = bookingRepository.findByCustomerOrderByCreatedAtDesc(customer, pageable);
        
        return bookings.map(this::convertToResponseDTO);
    }
    
    @Transactional
    public BookingResponseDTO confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking cannot be confirmed");
        }
        
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setConfirmedAt(LocalDateTime.now());
        booking = bookingRepository.save(booking);
        
        return convertToResponseDTO(booking);
    }
    
    @Transactional
    public BookingResponseDTO cancelBooking(Long bookingId, String reason) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        if (booking.getStatus() == BookingStatus.COMPLETED || 
            booking.getStatus() == BookingStatus.CANCELED) {
            throw new RuntimeException("Booking cannot be cancelled");
        }
        
        booking.setStatus(BookingStatus.CANCELED);
        booking.setCancelledAt(LocalDateTime.now());
        if (reason != null) {
            booking.setNotes(booking.getNotes() + "\nCancellation reason: " + reason);
        }
        booking = bookingRepository.save(booking);
        
        return convertToResponseDTO(booking);
    }
    
    public List<LocalTime[]> getAvailableTimeSlots(Long courtId, LocalDate date) {
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new RuntimeException("Court not found"));
        
        List<Booking> existingBookings = bookingRepository.findByCourtAndBookingDateOrderByStartTime(court, date);
        
        // Generate available time slots (simplified logic)
        // This would typically be more complex based on court opening hours
        List<LocalTime[]> availableSlots = generateTimeSlots(
            LocalTime.parse(court.getOpeningTime()), 
            LocalTime.parse(court.getClosingTime())
        );
        
        // Filter out booked slots
        return availableSlots.stream()
                .filter(slot -> !isSlotBooked(slot, existingBookings))
                .collect(Collectors.toList());
    }
    
    private void validateTimeSlot(Court court, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // Check if time slot is available
        List<BookingStatus> activeStatuses = Arrays.asList(BookingStatus.PENDING, BookingStatus.CONFIRMED);
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(
            court, date, startTime, endTime, activeStatuses);
        
        if (!conflictingBookings.isEmpty()) {
            throw new RuntimeException("Time slot is not available");
        }
        
        // Check if booking is within court operating hours
        LocalTime openingTime = LocalTime.parse(court.getOpeningTime());
        LocalTime closingTime = LocalTime.parse(court.getClosingTime());
        
        if (startTime.isBefore(openingTime) || endTime.isAfter(closingTime)) {
            throw new RuntimeException("Booking time is outside court operating hours");
        }
        
        // Check minimum booking duration (e.g., 1 hour)
        Duration duration = Duration.between(startTime, endTime);
        if (duration.toMinutes() < 60) {
            throw new RuntimeException("Minimum booking duration is 1 hour");
        }
    }
    
    private List<LocalTime[]> generateTimeSlots(LocalTime opening, LocalTime closing) {
        List<LocalTime[]> slots = new java.util.ArrayList<>();
        LocalTime current = opening;
        
        while (current.plusHours(1).isBefore(closing) || current.plusHours(1).equals(closing)) {
            slots.add(new LocalTime[]{current, current.plusHours(1)});
            current = current.plusMinutes(30); // 30-minute intervals
        }
        
        return slots;
    }
    
    private boolean isSlotBooked(LocalTime[] slot, List<Booking> bookings) {
        return bookings.stream()
                .anyMatch(booking -> 
                    (slot[0].isBefore(booking.getEndTime()) && slot[1].isAfter(booking.getStartTime())));
    }
    
    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        BookingResponseDTO dto = modelMapper.map(booking, BookingResponseDTO.class);
        dto.setStatus(booking.getStatus().name());
        dto.setCourt(courtService.getCourtById(booking.getCourt().getId()).orElse(null));
        return dto;
    }
}