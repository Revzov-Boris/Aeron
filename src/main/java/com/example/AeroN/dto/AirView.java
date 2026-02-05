package com.example.AeroN.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirView {
    private String icao24;
    private String country;
    private String category;
    private LocalDateTime lastContact;
    private int timePosition;
    private String callSign;
    private double latitude;
    private double longitude;
    private double baroAltitude;
    private double geoAltitude;
    private double velocity;
    private double verticalRate;
    private double trueTrack;
    private boolean onGround;
    private boolean spi;
    private int positionSource;
    private String squawk;
}
