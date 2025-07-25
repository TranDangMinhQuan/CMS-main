package com.badminton.booking.controller;

import com.badminton.booking.entity.Review;
import com.badminton.booking.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasAnyRole('MEMBER', 'STAFF', 'OWNER')")
    public ResponseEntity<Review> createReview(@RequestBody Review review, Authentication authentication) {
        Review createdReview = reviewService.createReview(review, authentication.getName());
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping("/court/{courtId}")
    public ResponseEntity<List<Review>> getReviewsByCourt(@PathVariable Long courtId) {
        return ResponseEntity.ok(reviewService.getReviewsByCourt(courtId));
    }

    @GetMapping("/court/{courtId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long courtId) {
        return ResponseEntity.ok(reviewService.getAverageRatingForCourt(courtId));
    }

    @GetMapping("/my-reviews")
    @PreAuthorize("hasAnyRole('MEMBER', 'STAFF', 'OWNER')")
    public ResponseEntity<List<Review>> getUserReviews(Authentication authentication) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(authentication.getName()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEMBER', 'STAFF', 'OWNER')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }
}