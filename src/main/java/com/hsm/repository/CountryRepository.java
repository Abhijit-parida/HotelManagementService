package com.hsm.repository;

import com.hsm.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

  Optional<Country> findByCountryName(String countryName);
}