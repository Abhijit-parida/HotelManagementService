package com.hsm.repository;

import com.hsm.entity.AppUser;
import com.hsm.entity.Property;
import com.hsm.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByAppUserId(AppUser appUserId);

    boolean existsByAppUserIdAndPropertyId(AppUser appUserId, Property propertyId);
}