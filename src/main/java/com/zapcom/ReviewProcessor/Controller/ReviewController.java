package com.zapcom.ReviewProcessor.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zapcom.ReviewProcessor.Entity.Review;
import com.zapcom.ReviewProcessor.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/review")
    public ResponseEntity<String> getReviews() throws JsonProcessingException {
        reviewService.fetchAndSaveReviews();
        return new ResponseEntity<>("Sucess", HttpStatus.OK);
    }
    @GetMapping("/allreviews")
    public ResponseEntity<List<Review>> getAllReviews(){
        return new ResponseEntity<>(reviewService.fecthAllReviews(), HttpStatus.OK);
    }
    @GetMapping("/category/{type}")
    public ResponseEntity<List<Review>> getReviewsByCategory(@PathVariable String type) {
        return new ResponseEntity<>(reviewService.getReviewsByCategory(type),HttpStatus.OK);
    }

    @GetMapping("/latest/good")
    public ResponseEntity<List<Review>> getLatestGoodReviews() {
        return new ResponseEntity<>(reviewService.getLatestGoodReviews(),HttpStatus.OK);
    }

    @GetMapping("/latest/bad")
    public ResponseEntity<List<Review>> getLatestBadReviews() {
        return new ResponseEntity<>(reviewService.getLatestBadReviews(),HttpStatus.OK);
    }

    @GetMapping("/latest/avg")
    public ResponseEntity<List<Review>> getLatestAvgReviews() {
        return new ResponseEntity<>(reviewService.getLatestAvgReviews(),HttpStatus.OK);
    }
}
