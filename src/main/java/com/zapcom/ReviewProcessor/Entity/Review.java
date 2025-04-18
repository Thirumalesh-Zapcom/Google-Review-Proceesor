package com.zapcom.ReviewProcessor.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewId;

    private String reviewerName;

    @Lob
    private String content;

    private int rating; // rating out of 5 or 10

    private LocalDateTime reviewedAt;

    // Optional: Hotel name or ID, if needed (though you're storing reviews for only one hotel)
    private String companay = "Cult Fit Madhapur"; // hardcoded or configured externally

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;
    public Review() {
        // default constructor
    }

    public Review(String review_id,String reviewerName, String content, int rating, LocalDateTime reviewedAt,String category) {
        this.reviewId=review_id;
        this.reviewerName = reviewerName;
        this.content = content;
        this.rating = rating;
        this.reviewedAt = reviewedAt;
        this.category=category;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getCompanay() {
        return companay;
    }

    public void setCompanay(String companay) {
        this.companay = companay;
    }
}
