package com.zapcom.ReviewProcessor.Repository;

import com.zapcom.ReviewProcessor.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    public Review findByReviewId(String reviewId);
    List<Review> findByRatingGreaterThanEqual(int rating);
    List<Review> findByRatingLessThanEqual(int rating);
    List<Review> findByRating(int rating);

    List<Review> findTop10ByRatingGreaterThanEqualOrderByReviewedAtDesc(int rating);
    List<Review> findTop10ByRatingLessThanEqualOrderByReviewedAtDesc(int rating);
    List<Review> findTop10ByRatingOrderByReviewedAtDesc(int rating);
}
