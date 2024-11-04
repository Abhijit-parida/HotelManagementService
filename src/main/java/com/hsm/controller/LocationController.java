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

    // ---------------------- Create ------------------------ //

    @PostMapping("/add-location")
    public ResponseEntity<?> addLocations(@RequestBody LocationDto locationDto) {
        if (locationService.verifyLocation(locationDto)) {
            return new ResponseEntity<>("Location Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(locationService.addLocations(locationDto), HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //

    @GetMapping("/get/all-data")
    public ResponseEntity<List<LocationDto>> getAllLocation() {
        return new ResponseEntity<>(locationService.getAllLocations(), HttpStatus.OK);
    }
}
