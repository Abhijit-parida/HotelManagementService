package com.hsm.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {

    private String locationName;

    private String cityName;

    private String stateName;

    private String countryName;
}
