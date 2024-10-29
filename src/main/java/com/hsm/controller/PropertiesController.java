package com.hsm.controller;

import com.hsm.entity.Property;
import com.hsm.payload.PropertyDto;
import com.hsm.service.PropertiesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/property")
public class PropertiesController {

    private PropertiesService propertiesService;

    public PropertiesController(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    // ----------------------- Create ----------------------- //

    @PostMapping("/add-properties")
    public ResponseEntity<?> addProperties(@RequestBody PropertyDto propertyDto,
                                           @RequestParam Long countryId,
                                           @RequestParam Long cityId) {
        return new ResponseEntity<>(propertiesService.addProperties(propertyDto, countryId, cityId),
                HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //



    // ----------------------- Update ----------------------- //



    // ----------------------- Delete ----------------------- //


}
