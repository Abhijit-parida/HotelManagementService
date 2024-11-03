package com.hsm.controller;

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
        if (propertiesService.verifyLocation(propertyDto)) {
            return new ResponseEntity<>(propertiesService.addProperties(propertyDto), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Given Location not matched with Country or City", HttpStatus.INTERNAL_SERVER_ERROR);
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
