package com.hsm.controller;


import com.hsm.payload.HotelDto;
import com.hsm.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // ----------------------- Create ----------------------- //

    @PostMapping("/add-hotel")
    public ResponseEntity<?> addHotelName(@RequestBody HotelDto hotelDto) {
        if (hotelService.verifyHotel(hotelDto)) {
            return new ResponseEntity<>("Hotel Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(hotelService.addHotelName(hotelDto), HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //

    @GetMapping("/get/all-data")
    public ResponseEntity<List<HotelDto>> getAllHotel() {
        return new ResponseEntity<>(hotelService.getHotelName(), HttpStatus.OK);
    }

    // ----------------------- Update ----------------------- //

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable Long id,
                                         @RequestParam String updateHotel){
        if (hotelService.verifyHotelId(id)) {
            return new ResponseEntity<>(hotelService.updateHotelId(id,updateHotel), HttpStatus.OK);
        }
        return new ResponseEntity<>("Hotel Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateHotel(@RequestParam String hotelName,
                                         @RequestParam String updateHotel) {
        if(hotelService.verifyHotelName(hotelName)) {
            return new ResponseEntity<>(hotelService.updateHotelName(hotelName,updateHotel), HttpStatus.OK);
        }
        return new ResponseEntity<>("Hotel Not Found", HttpStatus.NOT_FOUND);
    }

    // ----------------------- Delete ----------------------- //

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        try {
            hotelService.deleteHotelById(id);
            return new ResponseEntity<>("Hotel deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/name")
    public ResponseEntity<?> deleteHotel(@RequestParam String hotelName) {
        try {
            hotelService.deleteHotelByName(hotelName);
            return new ResponseEntity<>("Hotel deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}