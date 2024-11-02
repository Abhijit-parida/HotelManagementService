package com.hsm.controller;

import com.hsm.entity.Country;
import com.hsm.payload.CountryDto;
import com.hsm.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    // ----------------------- Create ----------------------- //

    @PostMapping("/country-name")
    public ResponseEntity<?> addCountry(@RequestBody CountryDto countryDto) {
        Optional<Country> verified = countryService.verifyCountry(countryDto);
        if(verified.isPresent()) {
            return new ResponseEntity<>("Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(countryService.addCountryName(countryDto), HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //

    @GetMapping("/all/data")
    public ResponseEntity<List<CountryDto>> getAllCountry() {
        return new ResponseEntity<>(countryService.getCountryName(), HttpStatus.OK);
    }

    // ----------------------- Update ----------------------- //

    @PutMapping("/update")
    public ResponseEntity<?> updateCity(@RequestParam String countryName,
                                        @RequestParam String updateCountry) {
        if(countryService.verifyCountryName(countryName)) {
            return new ResponseEntity<>(countryService.updateCountryName(countryName,updateCountry), HttpStatus.OK);
        }
        return new ResponseEntity<>("Country Not Found", HttpStatus.NOT_FOUND);
    }

    // ----------------------- Delete ----------------------- //

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        try {
            countryService.deleteCountryById(id);
            return new ResponseEntity<>("Country deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
