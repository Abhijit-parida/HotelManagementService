package com.hsm.service;

import com.hsm.entity.Location;
import com.hsm.payload.LocationDto;
import com.hsm.repository.CityRepository;
import com.hsm.repository.CountryRepository;
import com.hsm.repository.LocationRepository;
import com.hsm.repository.StateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    // --------------------- Constructor -------------------- //

    public LocationService(LocationRepository locationRepository, CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository) {
        this.locationRepository = locationRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    // --------------------- Converting --------------------- //

    private Location convertDtoToEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setLocationName(locationDto.getLocationName());
        countryRepository.findByCountryName(locationDto.getCountryName()).ifPresent(location::setCountryId);
        stateRepository.findByStateName(locationDto.getStateName()).ifPresent(location::setStateId);
        cityRepository.findByCityName(locationDto.getCityName()).ifPresent(location::setCityId);
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

    // ----------------------- Create ----------------------- //

//    public boolean verifyLocation(LocationDto locationDto) {
//        return locationRepository.findByLocationName(locationDto.getLocationName()).isPresent();
//    }

    public boolean verifyCountry(LocationDto locationDto) {
        return countryRepository.findByCountryName(locationDto.getCountryName()).isEmpty();
    }

    public boolean verifyState(LocationDto locationDto) {
        return stateRepository.findByStateName(locationDto.getStateName()).isEmpty();
    }

    public boolean verifyCity(LocationDto locationDto) {
        return cityRepository.findByCityName(locationDto.getCityName()).isEmpty();
    }

    public LocationDto addLocation(LocationDto locationDto) {
        return convertEntityToDto(locationRepository.save(convertDtoToEntity(locationDto)));
    }

    // ------------------------ Read ------------------------ //

    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    // ----------------------- Update ----------------------- //

    public LocationDto updateLocationById(Long id, LocationDto locationDto) {
        if(locationRepository.findById(id).isPresent()) {
            Location location = locationRepository.findById(id).get();
            location.setLocationName(locationDto.getLocationName());
            countryRepository.findByCountryName(locationDto.getCountryName()).ifPresent(location::setCountryId);
            stateRepository.findByStateName(locationDto.getStateName()).ifPresent(location::setStateId);
            cityRepository.findByCityName(locationDto.getCityName()).ifPresent(location::setCityId);
            return convertEntityToDto(location);
        } else {
            return null;
        }
    }

    public LocationDto updateLocationByName(String locationName, LocationDto locationDto) {
        if (locationRepository.findByLocationName(locationName).isPresent()) {
            Location location = locationRepository.findByLocationName(locationName).get();
            countryRepository.findByCountryName(locationDto.getCountryName()).ifPresent(location::setCountryId);
            stateRepository.findByStateName(locationDto.getStateName()).ifPresent(location::setStateId);
            cityRepository.findByCityName(locationDto.getCityName()).ifPresent(location::setCityId);
            return convertEntityToDto(location);
        } else {
            return null;
        }
    }

    // ----------------------- Delete ----------------------- //

    @Transactional
    public void deleteLocationById(Long id) {
        if (locationRepository.findById(id).isPresent()) {
            locationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Location with ID " + id + " does not exist.");
        }
    }

    @Transactional
    public void deleteLocationByName(String locationName) {
        if (locationRepository.findByLocationName(locationName).isPresent()) {
            locationRepository.deleteById(locationRepository.findByLocationName(locationName).get().getId());
        } else {
            throw new RuntimeException("Location with name ( " + locationName + " ) does not exist.");
        }
    }
}
