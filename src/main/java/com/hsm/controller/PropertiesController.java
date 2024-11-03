package com.hsm.controller;

import com.hsm.entity.City;
import com.hsm.entity.Country;
import com.hsm.entity.Hotels;
import com.hsm.payload.PropertyDto;
import com.hsm.service.PropertiesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/property")
public class PropertiesController {

    private final PropertiesService propertiesService;

    public PropertiesController(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    // ----------------------- Create ----------------------- //

    @PostMapping("/add-properties")
    public ResponseEntity<?> addProperties(@RequestBody PropertyDto propertyDto) {
        Optional<Country> country = propertiesService.verifyCountry(propertyDto);
        if (country.isEmpty()) {
            return new ResponseEntity<>("Country Name Is Not Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<City> city = propertiesService.verifyCity(propertyDto);
        if (city.isEmpty()) {
            return new ResponseEntity<>("City Name Is Not Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Hotels> hotels = propertiesService.verifyHotel(propertyDto);
        if (hotels.isEmpty()) {
            return new ResponseEntity<>("Hotel Name Is Not Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(propertiesService.addProperties(propertyDto), HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //

    @GetMapping("/get/all-data")
    public ResponseEntity<List<PropertyDto>> getAllData() {
        return new ResponseEntity<>(propertiesService.getAllProperties(), HttpStatus.OK);
    }


    // ----------------------- Update ----------------------- //

    @PutMapping("/update/{id}")
    public ResponseEntity<Optional<PropertyDto>> updateData(@PathVariable Long id,
                                                            @RequestBody PropertyDto propertyDto) {
        return new ResponseEntity<>(propertiesService.updateProperty(id,propertyDto), HttpStatus.OK);
    }

    // ----------------------- Delete ----------------------- //

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        if (propertiesService.verifyDeleteId(id)) {
            propertiesService.deleteProperty(id);
            return new ResponseEntity<>("File Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
    }
}
