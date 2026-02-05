package com.example.AeroN.enteties;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conditions")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConditionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "air_id")
    private AirEntity air;
    private LocalDateTime time;
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
