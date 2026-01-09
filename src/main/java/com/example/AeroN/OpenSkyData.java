package com.example.AeroN;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OpenSkyData {
    private Integer time;
    private List<AirData> airDataList;
}