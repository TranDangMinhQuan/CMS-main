package com.badminton.booking.repository;

import com.badminton.booking.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    Optional<Court> findByCourtName(String courtName);
    List<Court> findByStatus(Court.CourtStatus status);
    
    @Query("SELECT c FROM Court c WHERE c.courtName LIKE %:name%")
    List<Court> findByCourtNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM Court c WHERE c.id NOT IN " +
           "(SELECT DISTINCT b.court.id FROM Booking b WHERE b.status IN ('PENDING', 'CONFIRMED') " +
           "AND ((b.startTime <= :endTime AND b.endTime >= :startTime)))")
    List<Court> findAvailableCourts(@Param("startTime") LocalDateTime startTime, 
                                   @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.court.id = :courtId")
    Double getAverageRating(@Param("courtId") Long courtId);
}