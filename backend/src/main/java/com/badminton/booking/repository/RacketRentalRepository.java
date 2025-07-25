package com.badminton.booking.repository;

import com.badminton.booking.entity.RacketRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RacketRentalRepository extends JpaRepository<RacketRental, Long> {
    Optional<RacketRental> findByBookingId(Long bookingId);
    
    @Query("SELECT SUM(rr.totalAmount) FROM RacketRental rr WHERE DATE(rr.createdAt) = CURRENT_DATE")
    BigDecimal getTodayRacketRevenue();
    
    @Query("SELECT SUM(rr.totalAmount) FROM RacketRental rr WHERE MONTH(rr.createdAt) = :month AND YEAR(rr.createdAt) = :year")
    BigDecimal getMonthlyRacketRevenue(@Param("month") int month, @Param("year") int year);
}