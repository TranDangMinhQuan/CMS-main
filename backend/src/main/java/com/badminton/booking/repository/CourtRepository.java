package com.badminton.booking.repository;

import com.badminton.booking.entity.Court;
import com.badminton.booking.enums.CourtStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    List<Court> findByStatus(CourtStatus status);
    
    @Query("SELECT c FROM Court c WHERE c.status = 'AVAILABLE' AND c.id NOT IN " +
           "(SELECT b.court.id FROM Booking b WHERE b.status = 'CONFIRMED' AND " +
           "((b.startTime <= :endTime AND b.endTime >= :startTime)))")
    List<Court> findAvailableCourts(@Param("startTime") LocalDateTime startTime, 
                                   @Param("endTime") LocalDateTime endTime);
}