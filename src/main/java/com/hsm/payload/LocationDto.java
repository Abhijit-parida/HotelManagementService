package com.hsm.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {

    private String locationName;

    private String countryName;

    private String stateName;

    private String cityName;
}
