package com.badminton.booking.controller;

import com.badminton.booking.entity.Booking;
import com.badminton.booking.entity.Payment;
import com.badminton.booking.service.BookingService;
import com.badminton.booking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/revenue/today")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<BigDecimal> getTodayRevenue() {
        return ResponseEntity.ok(paymentService.getTodayRevenue());
    }

    @GetMapping("/revenue/monthly")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<BigDecimal> getMonthlyRevenue(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(paymentService.getTotalRevenue(month, year));
    }

    @GetMapping("/transactions/today")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<List<Payment>> getTodayTransactions() {
        return ResponseEntity.ok(paymentService.getTodayPayments());
    }

    @GetMapping("/bookings/today")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<List<Booking>> getTodayBookings() {
        return ResponseEntity.ok(bookingService.getTodayBookings());
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('STAFF', 'OWNER')")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("todayRevenue", paymentService.getTodayRevenue());
        dashboard.put("todayBookings", bookingService.getTodayBookings().size());
        dashboard.put("todayTransactions", paymentService.getTodayPayments());
        return ResponseEntity.ok(dashboard);
    }
}