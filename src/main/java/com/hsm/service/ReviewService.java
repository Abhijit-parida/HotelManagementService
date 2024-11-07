package com.hsm.service;

import com.hsm.entity.AppUser;
import com.hsm.entity.Property;
import com.hsm.entity.Review;
import com.hsm.repository.PropertyRepository;
import com.hsm.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;

    // -------------------- Constructor --------------------- //

    public ReviewService(ReviewRepository reviewRepository,
                         PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    // ----------------------- Create ----------------------- //

    public boolean verifyUniqueReview(Long propertyId, AppUser appUserId) {
        return reviewRepository.existsByAppUserIdAndPropertyId(appUserId, propertyRepository.findById(propertyId).get());
    }

    public Review addNewReviews(Review review, Long propertyId, AppUser appUserId) {
        Property property = propertyRepository.findById(propertyId).get();
        review.setPropertyId(property);
        review.setAppUserId(appUserId);
        return reviewRepository.save(review);
    }

    // ------------------------ Read ------------------------ //

    public List<Review> getAllReviews(AppUser appUserId) {
        return reviewRepository.findByAppUserId(appUserId);
    }

    // ----------------------- Update ----------------------- //
    // ----------------------- Create ----------------------- //
}
