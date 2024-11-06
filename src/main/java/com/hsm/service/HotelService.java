package com.hsm.service;


import com.hsm.entity.Hotels;
import com.hsm.payload.HotelDto;
import com.hsm.repository.HotelsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelsRepository hotelsRepository;
    private final ModelMapper modelMapper;

    // --------------------- Constructor -------------------- //

    public HotelService(HotelsRepository hotelsRepository, ModelMapper modelMapper) {
        this.hotelsRepository = hotelsRepository;
        this.modelMapper = modelMapper;
    }

    // ---------------------- Mapping ----------------------- //

    Hotels mapToEntity(HotelDto hotelDto) {
        return modelMapper.map(hotelDto, Hotels.class);
    }
    HotelDto mapToDto(Hotels hotels) {
        return modelMapper.map(hotels, HotelDto.class);
    }

    // ----------------------- Create ----------------------- //

    public boolean verifyHotel(HotelDto hotelDto) {
        return hotelsRepository.findByHotelName(hotelDto.getHotelName()).isPresent();
    }
    public HotelDto addHotelName(HotelDto hotelDto) {
        return mapToDto(hotelsRepository.save(mapToEntity(hotelDto)));
    }

    // ------------------------ Read ------------------------ //

    public List<HotelDto> getHotelName() {
        return hotelsRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // ----------------------- Update ----------------------- //

    public boolean verifyHotelId(Long id) {
        return hotelsRepository.findById(id).isPresent();
    }

    public HotelDto updateHotelId(Long id, String updateHotel) {
        Hotels hotels = hotelsRepository.findById(id).get();
        hotels.setHotelName(updateHotel);
        return mapToDto(hotelsRepository.save(hotels));
    }

    public boolean verifyHotelName(String hotelName) {
        return hotelsRepository.findByHotelName(hotelName).isPresent();
    }
    public HotelDto updateHotelName(String hotelName, String updateHotel) {
        Hotels hotels = hotelsRepository.findByHotelName(hotelName).get();
        hotels.setHotelName(updateHotel);
        return mapToDto(hotelsRepository.save(hotels));
    }

    // ----------------------- Delete ----------------------- //

    @Transactional
    public void deleteHotelById(Long id) {
        if (hotelsRepository.findById(id).isPresent()) {
            hotelsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Hotel with ID " + id + " does not exist.");
        }
    }

    @Transactional
    public void deleteHotelByName(String hotelName) {
        if (hotelsRepository.findByHotelName(hotelName).isPresent()) {
            hotelsRepository.deleteById(hotelsRepository.findByHotelName(hotelName).get().getId());
        } else {
            throw new RuntimeException("City with name ( " + hotelName + " ) does not exist.");
        }
    }
}
