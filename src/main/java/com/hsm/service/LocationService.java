package com.hsm.service;

import com.hsm.entity.Location;
import com.hsm.payload.LocationDto;
import com.hsm.repository.CityRepository;
import com.hsm.repository.CountryRepository;
import com.hsm.repository.LocationRepository;
import com.hsm.repository.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    // -------------------- Constructor --------------------- //

    public LocationService(LocationRepository locationRepository,
                           CountryRepository countryRepository,
                           CityRepository cityRepository, StateRepository stateRepository) {
        this.locationRepository = locationRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    // --------------------- Converting --------------------- //

    private Location convertDtoToEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setLocationName(locationDto.getLocationName());
        countryRepository.findByCountryName(locationDto.getCountryName()).ifPresent(location::setCountryId);
        cityRepository.findByCityName(locationDto.getCityName()).ifPresent(location::setCityId);
        stateRepository.findByStateName(locationDto.getStateName()).ifPresent(location::setStateId);
        return location;
    }

    private LocationDto convertEntityToDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLocationName(location.getLocationName());
        locationDto.setCountryName(location.getCountryId().getCountryName());
        locationDto.setStateName(location.getStateId().getStateName());
        locationDto.setCityName(location.getCityId().getCityName());
        return locationDto;
    }

    // ---------------------- Create ------------------------ //

    public boolean verifyLocation(LocationDto locationDto) {
        return locationRepository.findByLocationName(locationDto.getLocationName()).isPresent();
    }

    public LocationDto addLocations(LocationDto locationDto) {
        return convertEntityToDto(locationRepository.save(convertDtoToEntity(locationDto)));
    }

    // ------------------------ Read ------------------------ //

    public List<LocationDto> getAllProperties() {
        return locationRepository.findAll().stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
