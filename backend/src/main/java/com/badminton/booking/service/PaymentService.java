package com.badminton.booking.service;

import com.badminton.booking.entity.Payment;
import com.badminton.booking.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment updatePaymentStatus(Long paymentId, PaymentStatus status);
    Payment getPaymentByBookingId(Long bookingId);
    List<Payment> getTodayPayments();
    BigDecimal getTodayRevenue();
    BigDecimal getMonthlyRevenue(int month, int year);
    BigDecimal getTotalRevenue(int month, int year); // Including racket rentals
}