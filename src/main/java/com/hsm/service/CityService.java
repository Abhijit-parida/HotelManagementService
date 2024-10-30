package com.hsm.service;

import com.hsm.entity.City;
import com.hsm.payload.CityDto;
import com.hsm.payload.CountryDto;
import com.hsm.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    // ----------------------- Mapping ----------------------- //

    City mapToEntity(CityDto cityDto) {
        return modelMapper.map(cityDto, City.class);
    }
    CityDto mapToDto(City city) {
        return modelMapper.map(city, CityDto.class);
    }

    // ----------------------- Create ----------------------- //

    public Optional<City> verifyCity(CityDto cityDto) {
        return cityRepository.findByCityName(mapToEntity(cityDto).getCityName());
    }
    public CityDto addCityName(CityDto cityDto) {
        return mapToDto(cityRepository.save(mapToEntity(cityDto)));
    }

    // ------------------------ Read ------------------------ //

    public List<CityDto> getCityName() {
        return cityRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ----------------------- Update ----------------------- //

    public boolean verifyCityName(String cityName) {
        if(cityRepository.findByCityName(cityName).isPresent()) {
            return true;
        }
        return false;
    }
    public CityDto updateCityName(String cityName, String updateCity) {
        City city = cityRepository.findByCityName(cityName).get();
        city.setCityName(updateCity);
        return mapToDto(cityRepository.save(city));
    }


    // ----------------------- Delete ----------------------- //

}
