package com.hsm.service;

import com.hsm.entity.*;
import com.hsm.payload.LocationDto;
import com.hsm.payload.PropertyDto;
import com.hsm.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertiesService {

    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final HotelsRepository hotelsRepository;
    private final LocationRepository locationRepository;

    // --------------------- Constructor -------------------- //

    public PropertiesService(PropertyRepository propertyRepository,
                             CityRepository cityRepository,
                             CountryRepository countryRepository,
                             HotelsRepository hotelsRepository,
                             LocationRepository locationRepository) {
        this.propertyRepository = propertyRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.hotelsRepository = hotelsRepository;
        this.locationRepository = locationRepository;
    }

    // --------------------- Converting --------------------- //

    private Property convertDtoToEntity(PropertyDto propertyDto) {
        Property property = new Property();
        property.setNoOfGuests(propertyDto.getNoOfGuests());
        property.setNoOfBedrooms(propertyDto.getNoOfBedrooms());
        property.setNoOfBeds(propertyDto.getNoOfBeds());
        property.setNoOfBathrooms(propertyDto.getNoOfBathrooms());
        countryRepository.findByCountryName(propertyDto.getCountryName()).ifPresent(property::setCountryId);
        cityRepository.findByCityName(propertyDto.getCityName()).ifPresent(property::setCityId);
        hotelsRepository.findByHotelName(propertyDto.getHotelName()).ifPresent(property::setHotelId);
        locationRepository.findByLocationName(propertyDto.getLocationName()).ifPresent(property::setLocationId);
        return property;
    }

    private PropertyDto convertEntityToDto(Property property) {
        PropertyDto dto = new PropertyDto();
        dto.setNoOfGuests(property.getNoOfGuests());
        dto.setNoOfBedrooms(property.getNoOfBedrooms());
        dto.setNoOfBeds(property.getNoOfBeds());
        dto.setNoOfBathrooms(property.getNoOfBathrooms());
        dto.setCountryName(property.getCountryId().getCountryName());
        dto.setCityName(property.getCityId().getCityName());
        dto.setHotelName(property.getHotelId().getHotelName());
        dto.setLocationName(property.getLocationId().getLocationName());
        return dto;
    }

    // ----------------------- Create ----------------------- //

    public Boolean verifyLocation(PropertyDto propertyDto) {
        Optional<Location> byLocationName = locationRepository.findByLocationName(propertyDto.getLocationName());
        if (byLocationName.isPresent()) {
            LocationDto locationDto = new LocationDto();
            locationDto.setCountryName(byLocationName.get().getCountryId().getCountryName());
            locationDto.setCityName(byLocationName.get().getCityId().getCityName());
            if (locationDto.getCountryName().equals(propertyDto.getCountryName())) {
                return locationDto.getCityName().equals(propertyDto.getCityName());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public PropertyDto addProperties (PropertyDto propertyDto) {
        return convertEntityToDto(propertyRepository.save(convertDtoToEntity(propertyDto)));
    }

    // ------------------------ Read ------------------------ //

    public List<PropertyDto> getAllProperties() {
        return propertyRepository.findAll().stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    // ----------------------- Update ----------------------- //

    public Optional<PropertyDto> updateProperty(Long id, PropertyDto propertyDto) {
        Optional<Property> opData = propertyRepository.findById(id);
        if (opData.isPresent()) {
            Property property = opData.get();
            property.setNoOfGuests(propertyDto.getNoOfGuests());
            property.setNoOfBedrooms(propertyDto.getNoOfBedrooms());
            property.setNoOfBeds(propertyDto.getNoOfBeds());
            property.setNoOfBathrooms(propertyDto.getNoOfBathrooms());
            countryRepository.findByCountryName(propertyDto.getCountryName()).ifPresent(property::setCountryId);
            cityRepository.findByCityName(propertyDto.getCityName()).ifPresent(property::setCityId);
            hotelsRepository.findByHotelName(propertyDto.getHotelName()).ifPresent(property::setHotelId);
            return Optional.of(convertEntityToDto(propertyRepository.save(property)));
        }
        return Optional.empty();
    }

    // ----------------------- Delete ----------------------- //

    public boolean verifyDeleteId(Long id) {
        return propertyRepository.findById(id).isPresent();
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
