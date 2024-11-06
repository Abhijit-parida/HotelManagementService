package com.hsm.service;

import com.hsm.entity.AppUser;
import com.hsm.entity.Property;
import com.hsm.entity.Review;
import com.hsm.repository.PropertyRepository;
import com.hsm.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }


    public Review addNewReviews(Review review, Long propertiesId, AppUser appUser) {
        Property property = propertyRepository.findById(propertiesId).get();
        review.setPropertyId(property);
        review.setAppUserId(appUser);
        return reviewRepository.save(review);
    }
}
