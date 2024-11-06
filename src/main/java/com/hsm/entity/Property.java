package com.hsm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @Column(name = "hotel_name", nullable = false)
//    private String hotelName;

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
    @JoinColumn(name = "state_id")
    private State stateId;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotels hotelId;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationId;

    @OneToMany(mappedBy = "propertyId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;
}