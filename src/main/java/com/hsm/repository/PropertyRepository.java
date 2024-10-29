package com.hsm.repository;

import com.hsm.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    Optional<Property> findByHotelName(String hotelName);
}