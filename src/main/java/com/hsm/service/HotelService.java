package com.hsm.service;

import com.hsm.entity.Hotels;
import com.hsm.payload.HotelDto;
import com.hsm.repository.HotelsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public boolean verifyHotelName(HotelDto hotelDto) {
        return hotelsRepository.findByHotelName(hotelDto.getHotelName()).isPresent();
    }

    public HotelDto addHotels(HotelDto hotelDto) {
        return mapToDto(hotelsRepository.save(mapToEntity(hotelDto)));
    }

    // ------------------------ Read ------------------------ //

    public List<HotelDto> getAllHotelNames() {
        return hotelsRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // ----------------------- Update ----------------------- //

    public boolean verifyHotelsId(Long id) {
        return hotelsRepository.findById(id).isEmpty();
    }

    public HotelDto updateHotelId(Long id, String updateHotelName) {
        Hotels hotels = hotelsRepository.findById(id).get();
        hotels.setHotelName(updateHotelName);
        return mapToDto(hotelsRepository.save(hotels));
    }

    public boolean verifyHotelsName(String hotelName) {
        return hotelsRepository.findByHotelName(hotelName).isEmpty();
    }

    public HotelDto updateHotelName(String hotelName, String updateHotelName) {
        Hotels hotels = hotelsRepository.findByHotelName(hotelName).get();
        hotels.setHotelName(updateHotelName);
        return mapToDto(hotelsRepository.save(hotels));
    }

    // ----------------------- Delete ----------------------- //

    public void deleteHotelById(Long id) {
        if (hotelsRepository.findById(id).isPresent()) {
            hotelsRepository.deleteById(id);
        } else {
            throw new RuntimeException("Hotel with ID " + id + " does not exist.");
        }
    }

    public void deleteHotelByName(String hotelName) {
        if (hotelsRepository.findByHotelName(hotelName).isPresent()) {
            hotelsRepository.deleteById(hotelsRepository.findByHotelName(hotelName).get().getId());
        } else {
            throw new RuntimeException("Hotel with name ( " + hotelName + " ) does not exist.");
        }
    }
}
