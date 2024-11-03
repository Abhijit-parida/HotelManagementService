package com.hsm.service;

import com.hsm.entity.Location;
import com.hsm.payload.LocationDto;
import com.hsm.repository.CityRepository;
import com.hsm.repository.CountryRepository;
import com.hsm.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    // -------------------- Constructor --------------------- //

    public LocationService(LocationRepository locationRepository,
                           CountryRepository countryRepository,
                           CityRepository cityRepository) {
        this.locationRepository = locationRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    // --------------------- Converting --------------------- //

    private Location convertDtoToEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setLocationName(locationDto.getLocationName());
        countryRepository.findByCountryName(locationDto.getCountryName()).ifPresent(location::setCountryId);
        cityRepository.findByCityName(locationDto.getCityName()).ifPresent(location::setCityId);
        return location;
    }

    private LocationDto convertEntityToDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setLocationName(location.getLocationName());
        locationDto.setCountryName(location.getCountryId().getCountryName());
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
}
