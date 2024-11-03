package com.hsm.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PropertyDto {

    private String hotelName;

    private String countryName;

    private String cityName;

    private String locationName;

    private Integer noOfGuests;

    private Integer noOfBedrooms;

    private Integer noOfBeds;

    private Integer noOfBathrooms;
}
