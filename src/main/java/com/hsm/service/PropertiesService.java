package com.hsm.service;

import com.hsm.entity.Property;
import com.hsm.payload.PropertyDto;
import com.hsm.repository.CityRepository;
import com.hsm.repository.CountryRepository;
import com.hsm.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {

    private PropertyRepository propertyRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    public PropertiesService(PropertyRepository propertyRepository,
                             CityRepository cityRepository,
                             CountryRepository countryRepository,
                             ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    // ----------------------- Mapping ----------------------- //

    Property mapToEntity(PropertyDto propertyDto) {
        return modelMapper.map(propertyDto, Property.class);
    }
    PropertyDto mapToDto(Property property) {
        return modelMapper.map(property, PropertyDto.class);
    }

    // ----------------------- Create ----------------------- //

    public PropertyDto addProperties(PropertyDto propertyDto,
                                     Long countryId,
                                     Long cityId) {
        Property add = new Property();
        add.setHotelName(propertyDto.getHotelName());
        add.setNoOfGuests(propertyDto.getNoOfGuests());
        add.setNoOfBedrooms(propertyDto.getNoOfBedrooms());
        add.setNoOfBeds(propertyDto.getNoOfBeds());
        add.setNoOfBathrooms(propertyDto.getNoOfBathrooms());
        add.setCountryId(countryRepository.findById(countryId).get());
        add.setCityId(cityRepository.findById(cityId).get());
        return mapToDto(propertyRepository.save(add));
    }

    // ------------------------ Read ------------------------ //



    // ----------------------- Update ----------------------- //



    // ----------------------- Delete ----------------------- //


}
