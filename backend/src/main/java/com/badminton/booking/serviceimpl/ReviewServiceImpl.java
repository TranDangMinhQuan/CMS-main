package com.badminton.booking.serviceimpl;

import com.badminton.booking.entity.Review;
import com.badminton.booking.repository.ReviewRepository;
import com.badminton.booking.service.CourtService;
import com.badminton.booking.service.ReviewService;
import com.badminton.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourtService courtService;

    @Override
    public Review createReview(Review review, String username) {
        review.setUser(userService.getUserByUsername(username));
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByCourt(Long courtId) {
        return reviewRepository.findByCourtIdOrderByCreatedAtDesc(courtId);
    }

    @Override
    public List<Review> getReviewsByUser(String username) {
        Long userId = userService.getUserByUsername(username).getId();
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public Double getAverageRatingForCourt(Long courtId) {
        Double average = reviewRepository.getAverageRatingForCourt(courtId);
        return average != null ? average : 0.0;
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}