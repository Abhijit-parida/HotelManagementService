package com.hsm.controller;

import com.hsm.payload.StateDto;
import com.hsm.service.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/state")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    // ----------------------- Create ----------------------- //

    @PostMapping("/state-name")
    public ResponseEntity<?> addStateName(@RequestBody StateDto stateDto) {
        if(stateService.verifyState(stateDto)) {
            return new ResponseEntity<>("Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(stateService.addStateName(stateDto), HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //

    @GetMapping("get/all-data")
    public ResponseEntity<List<StateDto>> getAllCountry() {
        return new ResponseEntity<>(stateService.getStateName(), HttpStatus.OK);
    }

    // ----------------------- Update ----------------------- //

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCityName(@PathVariable Long id,
                                            @RequestParam String updateState){
        if (stateService.verifyStateId(id)) {
            return new ResponseEntity<>("Country Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(stateService.updateStateId(id,updateState), HttpStatus.OK);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateCityName(@RequestParam String stateName,
                                            @RequestParam String updateState) {
        if(stateService.verifyStateName(stateName)) {
            return new ResponseEntity<>("Country Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stateService.updateStateName(stateName,updateState), HttpStatus.OK);
    }

    // ----------------------- Delete ----------------------- //

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id) {
        try {
            stateService.deleteStateById(id);
            return new ResponseEntity<>("Country deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/name")
    public ResponseEntity<?> deleteCountry(@RequestParam String countryName) {
        try {
            stateService.deleteStateByName(countryName);
            return new ResponseEntity<>("Hotel deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
