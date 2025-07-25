package com.badminton.booking.repository;

import com.badminton.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByCourtId(Long courtId);
    List<Booking> findByStatus(Booking.BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.status = :status")
    List<Booking> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Booking.BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.court.id = :courtId AND b.status IN ('PENDING', 'CONFIRMED') " +
           "AND ((b.startTime <= :endTime AND b.endTime >= :startTime))")
    List<Booking> findConflictingBookings(@Param("courtId") Long courtId, 
                                         @Param("startTime") LocalDateTime startTime, 
                                         @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT b FROM Booking b WHERE b.createdAt BETWEEN :startDate AND :endDate")
    List<Booking> findBookingsByDateRange(@Param("startDate") LocalDateTime startDate, 
                                         @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(b.totalAmount) FROM Booking b WHERE b.status = 'COMPLETED' " +
           "AND YEAR(b.createdAt) = :year AND MONTH(b.createdAt) = :month")
    Double getTotalRevenueByMonth(@Param("year") int year, @Param("month") int month);
    
    @Query("SELECT b FROM Booking b WHERE DATE(b.createdAt) = DATE(:date)")
    List<Booking> findBookingsByDate(@Param("date") LocalDateTime date);
}