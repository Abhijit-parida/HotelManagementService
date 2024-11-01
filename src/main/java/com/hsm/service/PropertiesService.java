package com.hsm.service;

import com.hsm.entity.City;
import com.hsm.entity.Country;
import com.hsm.entity.Property;
import com.hsm.payload.AppUserDto;
import com.hsm.payload.PropertyDto;
import com.hsm.repository.CityRepository;
import com.hsm.repository.CountryRepository;
import com.hsm.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertiesService {

    private final PropertyRepository propertyRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    public PropertiesService(PropertyRepository propertyRepository,
                             CityRepository cityRepository,
                             CountryRepository countryRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    // ----------------------- Mapping ----------------------- //

    private Property mapToEntity(PropertyDto propertyDto) {
        return modelMapper.map(propertyDto, Property.class);
    }

    private PropertyDto mapToDto(Property property) {
        PropertyDto dto = new PropertyDto();
        dto.setHotelName(property.getHotelName());
        dto.setNoOfGuests(property.getNoOfGuests());
        dto.setNoOfBedrooms(property.getNoOfBedrooms());
        dto.setNoOfBeds(property.getNoOfBeds());
        dto.setNoOfBathrooms(property.getNoOfBathrooms());
        dto.setCountryName(property.getCountryId().getCountryName());
        dto.setCityName(property.getCityId().getCityName());
        return dto;
    }

    // ----------------------- Create ----------------------- //

    public Optional<Country> verifyCountry(PropertyDto propertyDto) {
        return countryRepository.findByCountryName(propertyDto.getCountryName());
    }
    public Optional<City> verifyCity(PropertyDto propertyDto) {
        return cityRepository.findByCityName(propertyDto.getCityName());
    }
    public PropertyDto addProperties (PropertyDto propertyDto) {
        Property property = new Property();

        countryRepository.findByCountryName(propertyDto.getCountryName())
                .ifPresent(property::setCountryId);
        cityRepository.findByCityName(propertyDto.getCityName())
                .ifPresent(property::setCityId);

        property.setHotelName(propertyDto.getHotelName());
        property.setNoOfBedrooms(propertyDto.getNoOfBedrooms());
        property.setNoOfBeds(propertyDto.getNoOfBeds());
        property.setNoOfBathrooms(propertyDto.getNoOfBathrooms());
        property.setNoOfGuests(propertyDto.getNoOfGuests());

        return mapToDto(propertyRepository.save(property));
    }

    // ------------------------ Read ------------------------ //

    public List<PropertyDto> getAllProperties() {
        return propertyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    // ----------------------- Update ----------------------- //

    public Optional<PropertyDto> updateProperty(Long id, PropertyDto propertyDto) {
        Optional<Property> opData = propertyRepository.findById(id);

        if (opData.isPresent()) {
            Property property = opData.get();

            countryRepository.findByCountryName(propertyDto.getCountryName())
                    .ifPresent(property::setCountryId);
            cityRepository.findByCityName(propertyDto.getCityName())
                    .ifPresent(property::setCityId);

            property.setHotelName(propertyDto.getHotelName());
            property.setNoOfGuests(propertyDto.getNoOfGuests());
            property.setNoOfBedrooms(propertyDto.getNoOfBedrooms());
            property.setNoOfBeds(propertyDto.getNoOfBeds());
            property.setNoOfBathrooms(propertyDto.getNoOfBathrooms());

            return Optional.of(mapToDto(propertyRepository.save(property)));
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
