package com.example.AeroN.controllers;

import com.example.AeroN.OpenSkyData;
import com.example.AeroN.services.AirCollectorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
public class MapController {
    private AirCollectorService airCollectorService;

    public MapController(AirCollectorService airCollectorService) {
        this.airCollectorService = airCollectorService;
    }


    @GetMapping
    public OpenSkyData getAirs() {
        return airCollectorService.getData();
    }
}
