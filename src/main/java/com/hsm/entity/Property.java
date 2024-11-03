package com.hsm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "no_of_guests", nullable = false)
    private Integer noOfGuests;

    @Column(name = "no_of_bedrooms", nullable = false)
    private Integer noOfBedrooms;

    @Column(name = "no_of_beds", nullable = false)
    private Integer noOfBeds;

    @Column(name = "no_of_bathrooms", nullable = false)
    private Integer noOfBathrooms;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country countryId;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City cityId;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationId;

    @ManyToOne
    @JoinColumn(name = "hotels_id")
    private Hotels hotelId;
}