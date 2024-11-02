package com.hsm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "hotels")
public class Hotels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "hotel_name", nullable = false, length = 255)
    private String hotelName;

    @OneToMany(mappedBy = "hotelId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> properties;
}