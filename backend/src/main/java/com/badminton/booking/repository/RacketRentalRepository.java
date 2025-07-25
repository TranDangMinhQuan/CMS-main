package com.badminton.booking.repository;

import com.badminton.booking.entity.RacketRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RacketRentalRepository extends JpaRepository<RacketRental, Long> {
    List<RacketRental> findByBookingId(Long bookingId);
    
    @Query("SELECT SUM(r.totalAmount) FROM RacketRental r WHERE DATE(r.createdAt) = DATE(:date)")
    Double getTotalRacketRevenueByDate(@Param("date") LocalDateTime date);
    
    @Query("SELECT SUM(r.quantity) FROM RacketRental r WHERE DATE(r.createdAt) = DATE(:date)")
    Integer getTotalRacketsRentedByDate(@Param("date") LocalDateTime date);
}