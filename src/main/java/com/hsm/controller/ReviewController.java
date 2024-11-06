package com.hsm.controller;

import com.hsm.entity.AppUser;
import com.hsm.entity.Review;
import com.hsm.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add-review")
    public ResponseEntity<Review> addReviews(@RequestBody Review review,
                                              @RequestParam Long propertiesId,
                                              @AuthenticationPrincipal AppUser appUser) {
        return new ResponseEntity<>(reviewService.addNewReviews(review,propertiesId,appUser), HttpStatus.CREATED);
    }
}
