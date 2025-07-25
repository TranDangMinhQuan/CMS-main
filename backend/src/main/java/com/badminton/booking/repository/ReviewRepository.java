package com.badminton.booking.repository;

import com.badminton.booking.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCourtId(Long courtId);
    List<Review> findByUserId(Long userId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.court.id = :courtId")
    Double getAverageRatingForCourt(@Param("courtId") Long courtId);
    
    @Query("SELECT r FROM Review r WHERE r.court.id = :courtId ORDER BY r.createdAt DESC")
    List<Review> findByCourtIdOrderByCreatedAtDesc(@Param("courtId") Long courtId);
}