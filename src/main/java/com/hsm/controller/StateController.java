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
            return new ResponseEntity<>("State Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(stateService.addStateName(stateDto), HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //

    @GetMapping("get/all-data")
    public ResponseEntity<List<StateDto>> getAllState() {
        return new ResponseEntity<>(stateService.getStateName(), HttpStatus.OK);
    }

    // ----------------------- Update ----------------------- //

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStateName(@PathVariable Long id,
                                            @RequestParam String updateState){
        if (stateService.verifyStateId(id)) {
            return new ResponseEntity<>(stateService.updateStateId(id,updateState), HttpStatus.OK);
        }
        return new ResponseEntity<>("State Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateStateName(@RequestParam String stateName,
                                            @RequestParam String updateState) {
        if(stateService.verifyStateName(stateName)) {
            return new ResponseEntity<>(stateService.updateStateName(stateName,updateState), HttpStatus.OK);
        }
        return new ResponseEntity<>("State Not Found", HttpStatus.NOT_FOUND);
    }

    // ----------------------- Delete ----------------------- //

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteState(@PathVariable Long id) {
        try {
            stateService.deleteStateById(id);
            return new ResponseEntity<>("State deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/name")
    public ResponseEntity<?> deleteState(@RequestParam String stateName) {
        try {
            stateService.deleteStateByName(stateName);
            return new ResponseEntity<>("State deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}