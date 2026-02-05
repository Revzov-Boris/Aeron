package com.example.AeroN.enteties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "airs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String icao24;
    private String country;
    @Column(nullable = true)
    private Integer category;
    @OneToMany(mappedBy = "air", cascade = CascadeType.ALL)
    @OrderBy("time ASC")
    List<ConditionEntity> conditions;


    public AirEntity(String icao24, String country, Integer category) {
        this.icao24 = icao24;
        this.country = country;
        this.category = category;
    }
}
