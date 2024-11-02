package com.hsm.controller;

import com.hsm.entity.City;
import com.hsm.payload.CityDto;
import com.hsm.payload.CountryDto;
import com.hsm.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // ----------------------- Create ----------------------- //

    @PostMapping("/city-name")
    public ResponseEntity<?> addCity(@RequestBody CityDto cityDto) {
        Optional<City> verified = cityService.verifyCity(cityDto);
        if (verified.isPresent()) {
            return new ResponseEntity<>("Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cityService.addCityName(cityDto), HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //

    @GetMapping("/all/data")
    public ResponseEntity<List<CityDto>> getAllCity() {
        return new ResponseEntity<>(cityService.getCityName(), HttpStatus.OK);
    }

    // ----------------------- Update ----------------------- //

    @PutMapping("/update")
    public ResponseEntity<?> updateCity(@RequestParam String cityName,
                                        @RequestParam String updateCity) {
        if(cityService.verifyCityName(cityName)) {
            return new ResponseEntity<>(cityService.updateCityName(cityName,updateCity), HttpStatus.OK);
        }
        return new ResponseEntity<>("City Not Found", HttpStatus.NOT_FOUND);
    }

    // ----------------------- Delete ----------------------- //

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {
        try {
            cityService.deleteCityById(id);
            return new ResponseEntity<>("Country deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
