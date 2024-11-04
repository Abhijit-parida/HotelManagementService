package com.hsm.service;

import com.hsm.entity.City;
import com.hsm.payload.CityDto;
import com.hsm.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    // --------------------- Constructor -------------------- //

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    // ---------------------- Mapping ----------------------- //

    City mapToEntity(CityDto cityDto) {
        return modelMapper.map(cityDto, City.class);
    }
    CityDto mapToDto(City city) {
        return modelMapper.map(city, CityDto.class);
    }

    // ----------------------- Create ----------------------- //

    public boolean verifyCity(CityDto cityDto) {
        return cityRepository.findByCityName(cityDto.getCityName()).isPresent();
    }
    public CityDto addCityName(CityDto cityDto) {
        return mapToDto(cityRepository.save(mapToEntity(cityDto)));
    }

    // ------------------------ Read ------------------------ //

    public List<CityDto> getCityName() {
        return cityRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // ----------------------- Update ----------------------- //

    public boolean verifyCityId(Long id) {
        return cityRepository.findById(id).isPresent();
    }

    public CityDto updateCityId(Long id, String updateCity) {
        City city = cityRepository.findById(id).get();
        city.setCityName(updateCity);
        return mapToDto(cityRepository.save(city));
    }

    public boolean verifyCityName(String cityName) {
        return cityRepository.findByCityName(cityName).isPresent();
    }
    public CityDto updateCityName(String cityName, String updateCity) {
        City city = cityRepository.findByCityName(cityName).get();
        city.setCityName(updateCity);
        return mapToDto(cityRepository.save(city));
    }

    // ----------------------- Delete ----------------------- //

    public void deleteCityById(Long id) {
        if (cityRepository.findById(id).isPresent()) {
            cityRepository.deleteById(id);
        } else {
            throw new RuntimeException("City with ID " + id + " does not exist.");
        }
    }

    public void deleteCityByName(String cityName) {
        if (cityRepository.findByCityName(cityName).isPresent()) {
            cityRepository.deleteById(cityRepository.findByCityName(cityName).get().getId());
        } else {
            throw new RuntimeException("City with name ( " + cityName + " ) does not exist.");
        }
    }
}
