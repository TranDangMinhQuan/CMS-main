package com.badminton.booking.service;

import com.badminton.booking.entity.Review;

import java.util.List;

public interface ReviewService {
    Review createReview(Review review, String username);
    List<Review> getReviewsByCourt(Long courtId);
    List<Review> getReviewsByUser(String username);
    Double getAverageRatingForCourt(Long courtId);
    void deleteReview(Long id);
}