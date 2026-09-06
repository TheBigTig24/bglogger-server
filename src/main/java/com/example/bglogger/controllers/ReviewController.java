package com.example.bglogger.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bglogger.dto.ReviewDTO;
import com.example.bglogger.models.Review;
import com.example.bglogger.services.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(
        ReviewService reviewService
    ) {
        this.reviewService = reviewService;
    }
    
    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<ReviewDTO> postReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        Review review = reviewService.createNewReview(reviewDTO);

        ReviewDTO response = new ReviewDTO();
        response.setGameId(review.getGame().getId());
        response.setUserId(review.getUser().getId());
        response.setBody(review.getBody());
        response.setScoreCategory(review.getScoreCategory());
        response.setListPosition(review.getListPosition());
        
        return ResponseEntity.ok().body(response);
    }
}
