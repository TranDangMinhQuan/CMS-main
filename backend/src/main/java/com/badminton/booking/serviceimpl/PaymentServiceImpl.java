package com.badminton.booking.serviceimpl;

import com.badminton.booking.entity.Payment;
import com.badminton.booking.enums.PaymentStatus;
import com.badminton.booking.repository.PaymentRepository;
import com.badminton.booking.repository.RacketRentalRepository;
import com.badminton.booking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RacketRentalRepository racketRentalRepository;

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(status);
        if (status == PaymentStatus.PAID) {
            payment.setPaidAt(LocalDateTime.now());
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public List<Payment> getTodayPayments() {
        return paymentRepository.getTodayPayments();
    }

    @Override
    public BigDecimal getTodayRevenue() {
        BigDecimal todayRevenue = paymentRepository.getTodayRevenue();
        return todayRevenue != null ? todayRevenue : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getMonthlyRevenue(int month, int year) {
        BigDecimal monthlyRevenue = paymentRepository.getMonthlyRevenue(month, year);
        return monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalRevenue(int month, int year) {
        BigDecimal paymentRevenue = getMonthlyRevenue(month, year);
        BigDecimal racketRevenue = racketRentalRepository.getMonthlyRacketRevenue(month, year);
        if (racketRevenue == null) racketRevenue = BigDecimal.ZERO;
        return paymentRevenue.add(racketRevenue);
    }
}