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

    @PostMapping("/hotel-name")
    public ResponseEntity<?> addHotels(@RequestBody HotelDto hotelDto) {
        if (hotelService.verifyHotelName(hotelDto)) {
            return new ResponseEntity<>("Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(hotelService.addHotels(hotelDto), HttpStatus.CREATED);
    }

    // ------------------------ Read ------------------------ //

    @GetMapping("/get/all-data")
    public ResponseEntity<List<HotelDto>> getAllHotel() {
        return new ResponseEntity<>(hotelService.getAllHotelNames(), HttpStatus.OK);
    }

    // ----------------------- Update ----------------------- //

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHotelName(@PathVariable Long id,
                                             @RequestParam String updateHotelName){
        if (hotelService.verifyHotelsId(id)) {
            return new ResponseEntity<>("Hotel Not Found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(hotelService.updateHotelId(id,updateHotelName), HttpStatus.OK);
    }

    @PutMapping("/update/name")
    public ResponseEntity<?> updateHotelName(@RequestParam String hotelName,
                                             @RequestParam String updateHotelName) {
        if (hotelService.verifyHotelsName(hotelName)) {
            return new ResponseEntity<>("Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(hotelService.updateHotelName(hotelName,updateHotelName), HttpStatus.OK);
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
