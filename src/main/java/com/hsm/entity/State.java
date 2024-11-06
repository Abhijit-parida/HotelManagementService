package com.hsm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "state")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "state_name", nullable = false)
    private String stateName;

    @OneToMany(mappedBy = "stateId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> properties;
}