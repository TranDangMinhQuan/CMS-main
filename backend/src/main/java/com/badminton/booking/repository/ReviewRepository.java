package com.badminton.booking.repository;

import com.badminton.booking.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCourtId(Long courtId);
    List<Review> findByUserId(Long userId);
    List<Review> findByCourtIdOrderByCreatedAtDesc(Long courtId);
    List<Review> findByRating(Integer rating);
}