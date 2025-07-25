package com.badminton.booking.repository;

import com.badminton.booking.entity.Booking;
import com.badminton.booking.enums.BookingStatus;
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
    List<Booking> findByStatus(BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.status = :status")
    List<Booking> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.court.id = :courtId AND b.status = 'CONFIRMED' AND " +
           "((b.startTime <= :endTime AND b.endTime >= :startTime))")
    List<Booking> findConflictingBookings(@Param("courtId") Long courtId, 
                                         @Param("startTime") LocalDateTime startTime, 
                                         @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT b FROM Booking b WHERE DATE(b.createdAt) = CURRENT_DATE")
    List<Booking> findTodayBookings();
    
    @Query("SELECT b FROM Booking b WHERE MONTH(b.createdAt) = :month AND YEAR(b.createdAt) = :year")
    List<Booking> findBookingsByMonth(@Param("month") int month, @Param("year") int year);
}