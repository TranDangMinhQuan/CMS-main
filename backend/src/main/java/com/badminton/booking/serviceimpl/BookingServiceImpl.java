package com.badminton.booking.serviceimpl;

import com.badminton.booking.dto.BookingRequest;
import com.badminton.booking.entity.*;
import com.badminton.booking.enums.BookingStatus;
import com.badminton.booking.enums.PaymentMethod;
import com.badminton.booking.enums.PaymentStatus;
import com.badminton.booking.repository.BookingRepository;
import com.badminton.booking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private RacketRentalService racketRentalService;

    @Override
    @Transactional
    public Booking createBooking(BookingRequest bookingRequest, String username) {
        User user = userService.getUserByUsername(username);
        Court court = courtService.getCourtById(bookingRequest.getCourtId());

        // Check if court is available
        if (!isCourtAvailable(bookingRequest.getCourtId(), bookingRequest.getStartTime(), bookingRequest.getEndTime())) {
            throw new RuntimeException("Court is not available for the selected time");
        }

        // Calculate hours and total amount
        Duration duration = Duration.between(bookingRequest.getStartTime(), bookingRequest.getEndTime());
        long hours = duration.toHours();
        BigDecimal totalAmount = court.getHourlyRate().multiply(BigDecimal.valueOf(hours));

        // Apply coupon if provided
        if (bookingRequest.getCouponCode() != null && !bookingRequest.getCouponCode().isEmpty()) {
            totalAmount = couponService.applyCoupon(bookingRequest.getCouponCode(), totalAmount);
        }

        // Create booking
        Booking booking = new Booking(user, court, bookingRequest.getStartTime(), 
                                    bookingRequest.getEndTime(), totalAmount);
        booking.setNotes(bookingRequest.getNotes());
        booking = bookingRepository.save(booking);

        // Create payment
        Payment payment = new Payment(booking, totalAmount, bookingRequest.getPaymentMethod());
        if (bookingRequest.getPaymentMethod() == PaymentMethod.ONLINE) {
            payment.setStatus(PaymentStatus.PAID);
            payment.setPaidAt(LocalDateTime.now());
            booking.setStatus(BookingStatus.CONFIRMED);
        }
        paymentService.createPayment(payment);

        // Create racket rental if requested
        if (bookingRequest.getRacketQuantity() > 0) {
            racketRentalService.createRacketRental(booking.getId(), bookingRequest.getRacketQuantity());
        }

        // Use coupon if applied
        if (bookingRequest.getCouponCode() != null && !bookingRequest.getCouponCode().isEmpty()) {
            couponService.useCoupon(bookingRequest.getCouponCode());
        }

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getUserBookings(String username) {
        User user = userService.getUserByUsername(username);
        return bookingRepository.findByUserId(user.getId());
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public Booking updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getTodayBookings() {
        return bookingRepository.findTodayBookings();
    }

    @Override
    public List<Booking> getBookingsByMonth(int month, int year) {
        return bookingRepository.findBookingsByMonth(month, year);
    }

    @Override
    public boolean isCourtAvailable(Long courtId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(courtId, startTime, endTime);
        return conflictingBookings.isEmpty();
    }
}