package com.example.AeroN;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AirData {
    private String icao24;
    private String callsign;
    private String originCountry;
    private Integer timePosition;
    private Integer lastContact;
    private Double longitude;
    private Double latitude;
    private Double baroAltitude;
    private Boolean onGround;
    private Double velocity;
    private Double trueTrack;
    private Double verticalRate;
    private Double geoAltitude;
    private String squawk;
    private Boolean spi;
    private Integer positionSource;
    private Integer category;
}
