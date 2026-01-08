package com.example.AeroN.enteties;

import jakarta.persistence.*;

@Entity
@Table(name = "airs")
public class AirEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String icao24;
    private String country;
    private int category;
}
