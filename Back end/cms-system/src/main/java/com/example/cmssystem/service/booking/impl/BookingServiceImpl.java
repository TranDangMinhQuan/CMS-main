package com.example.cmssystem.service.booking.impl;

import com.example.cmssystem.dto.booking.BookingRequestDTO;
import com.example.cmssystem.dto.booking.BookingResponseDTO;
import com.example.cmssystem.entity.booking.*;
import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.enums.BookingStatus;
import com.example.cmssystem.exception.exceptions.ResourceNotFoundException;
import com.example.cmssystem.repository.booking.BookingPackageRepository;
import com.example.cmssystem.repository.booking.BookingRepository;
import com.example.cmssystem.repository.booking.CouponRepository;
import com.example.cmssystem.repository.court.CourtRepository;
import com.example.cmssystem.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingPackageRepository bookingPackageRepository;
    private final CouponRepository couponRepository;
    private final CourtRepository courtRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {
        // Lấy thông tin sân
        Court court = courtRepository.findById(requestDTO.getCourtId())
                .orElseThrow(() -> new ResourceNotFoundException("Court not found"));

        // Lấy thông tin gói đặt sân
        BookingPackage bookingPackage = bookingPackageRepository.findById(requestDTO.getPackageId())
                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));

        // Tính toán tổng giờ từ thời gian start-end
        long minutesBetween = Duration.between(requestDTO.getStartTime(), requestDTO.getEndTime()).toMinutes();
        BigDecimal hours = BigDecimal.valueOf(minutesBetween).divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP);

        // Kiểm tra thời lượng có đúng với gói không
        if (!minutesBetweenEquals(bookingPackage.getDurationMinutes(), minutesBetween)) {
            throw new IllegalArgumentException("The booking time does not match the selected package duration.");
        }

        // Giá trước giảm giá
        BigDecimal originalPrice = bookingPackage.getPrice();
        BigDecimal discount = BigDecimal.ZERO;

        // Áp dụng coupon nếu có
        if (requestDTO.getCouponCode() != null && !requestDTO.getCouponCode().isBlank()) {
            Coupon coupon = couponRepository.findByCode(requestDTO.getCouponCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Coupon not found"));

            if (Boolean.TRUE.equals(coupon.getIsActive()) && coupon.getValidUntil().isAfter(LocalDateTime.now())) {
                if (coupon.getDiscountAmount() != null) {
                    discount = coupon.getDiscountAmount();
                } else if (coupon.getDiscountPercent() != null) {
                    discount = originalPrice.multiply(BigDecimal.valueOf(coupon.getDiscountPercent())).divide(BigDecimal.valueOf(100));
                }
            }
        }

        // Tạo booking mới
        Booking booking = new Booking();
        booking.setBookingCode("BK-" + UUID.randomUUID().toString().substring(0, 8));
        booking.setCourt(court);
        booking.setBookingPackage(bookingPackage);
        booking.setCustomerName(requestDTO.getFullName());
        booking.setCustomerPhone(requestDTO.getPhone());
        booking.setCustomerEmail(requestDTO.getEmail());
        booking.setNotes("CCCD: " + requestDTO.getCccd());
        booking.setStartTime(requestDTO.getStartTime().toLocalTime());
        booking.setEndTime(requestDTO.getEndTime().toLocalTime());
        booking.setBookingDate(requestDTO.getStartTime().toLocalDate());

        booking.setTotalHours(hours);
        booking.setPricePerHour(originalPrice.divide(hours, 2, BigDecimal.ROUND_HALF_UP));
        booking.setTotalAmount(originalPrice);
        booking.setDiscountAmount(discount);
        booking.setFinalAmount(originalPrice.subtract(discount));
        booking.setStatus(BookingStatus.PENDING.name());
        booking.setCreatedAt(LocalDateTime.now());

        bookingRepository.save(booking);

        return modelMapper.map(booking, BookingResponseDTO.class);
    }

    @Override
    public BookingResponseDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return modelMapper.map(booking, BookingResponseDTO.class);
    }

    @Override
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> modelMapper.map(booking, BookingResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDTO checkInBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setStatus(BookingStatus.CHECKED_IN.name());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setConfirmedAt(LocalDateTime.now());
        bookingRepository.save(booking);
        return modelMapper.map(booking, BookingResponseDTO.class);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELED.name());
        booking.setCancelledAt(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    private boolean minutesBetweenEquals(Integer expectedMinutes, long actualMinutes) {
        // Cho phép lệch nhẹ vài phút (ví dụ 5 phút), để tránh lỗi do lệch giờ máy
        int tolerance = 5;
        return Math.abs(expectedMinutes - actualMinutes) <= tolerance;
    }
}
