package com.hsm.service;

import com.hsm.entity.City;
import com.hsm.entity.Country;
import com.hsm.payload.AppUserDto;
import com.hsm.payload.CityDto;
import com.hsm.payload.CountryDto;
import com.hsm.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    // ----------------------- Mapping ----------------------- //

    Country mapToEntity(CountryDto countryDto) {
        return modelMapper.map(countryDto, Country.class);
    }
    CountryDto mapToDto(Country country) {
        return modelMapper.map(country, CountryDto.class);
    }

    // ----------------------- Create ----------------------- //

    public Optional<Country> verifyCountry(CountryDto countryDto) {
        return countryRepository.findByCountryName(mapToEntity(countryDto).getCountryName());
    }
    public CountryDto addCountryName(CountryDto countryDto) {
        return mapToDto(countryRepository.save(mapToEntity(countryDto)));
    }

    // ------------------------ Read ------------------------ //

    public List<CountryDto> getCountryName() {
      return countryRepository.findAll()
              .stream()
              .map(this::mapToDto)
              .collect(Collectors.toList());
    }

    // ----------------------- Update ----------------------- //

    public boolean verifyCountryName(String countryName) {
        if(countryRepository.findByCountryName(countryName).isPresent()) {
            return true;
        }
        return false;
    }
    public CountryDto updateCountryName(String countryName, String updateCountry) {
        Country country = countryRepository.findByCountryName(countryName).get();
        country.setCountryName(updateCountry);
        return mapToDto(countryRepository.save(country));
    }

    // ----------------------- Delete ----------------------- //

}
