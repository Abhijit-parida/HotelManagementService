package com.hsm.controller;

import com.hsm.payload.LocationDto;
import com.hsm.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    // ----------------------- Create ----------------------- //

    @PostMapping("/add-location")
    public ResponseEntity<?> addLocation(@RequestBody LocationDto locationDto) {
//        if (locationService.verifyLocation(locationDto)) {
//            return new ResponseEntity<>("Location name Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        if (locationService.verifyCountry(locationDto)) {
            return new ResponseEntity<>("Country name not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (locationService.verifyState(locationDto)) {
            return new ResponseEntity<>("State name not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (locationService.verifyCity(locationDto)) {
            return new ResponseEntity<>("City name not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(locationService.addLocation(locationDto), HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //

    @GetMapping("/get/all-data")
    public ResponseEntity<List<LocationDto>> getAllLocationList() {
        return new ResponseEntity<>(locationService.getAllLocations(), HttpStatus.OK);
    }
    // ----------------------- Update ----------------------- //

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLocations(@PathVariable Long id,
                                             @RequestBody LocationDto locationDto) {
        return new ResponseEntity<>(locationService.updateLocationById(id, locationDto), HttpStatus.OK);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateLocations(@RequestParam String locationName,
                                             @RequestBody LocationDto locationDto) {
        return new ResponseEntity<>(locationService.updateLocationByName(locationName, locationDto), HttpStatus.OK);
    }

    // ----------------------- Delete ----------------------- //

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        try {
            locationService.deleteLocationById(id);
            return new ResponseEntity<>("Location deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/name")
    public ResponseEntity<?> deleteLocation(@RequestParam String locationName) {
        try {
            locationService.deleteLocationByName(locationName);
            return new ResponseEntity<>("Location deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
