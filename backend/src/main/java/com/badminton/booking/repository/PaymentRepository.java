package com.badminton.booking.repository;

import com.badminton.booking.entity.Payment;
import com.badminton.booking.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBookingId(Long bookingId);
    List<Payment> findByStatus(PaymentStatus status);
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'PAID' AND DATE(p.paidAt) = CURRENT_DATE")
    BigDecimal getTodayRevenue();
    
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'PAID' AND MONTH(p.paidAt) = :month AND YEAR(p.paidAt) = :year")
    BigDecimal getMonthlyRevenue(@Param("month") int month, @Param("year") int year);
    
    @Query("SELECT p FROM Payment p WHERE DATE(p.paidAt) = CURRENT_DATE AND p.status = 'PAID'")
    List<Payment> getTodayPayments();
}