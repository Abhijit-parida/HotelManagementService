package com.hsm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @OneToMany(mappedBy = "cityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> properties;
}