package com.example.AeroN.controllers;

import com.example.AeroN.dto.AirView;
import com.example.AeroN.services.AirService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/map")
public class MapController {
    private AirService airService;

    public MapController(AirService airService) {
        this.airService = airService;
    }


    @GetMapping
    public List<AirView> getAirs() {
        return airService.getAllView();
    }
}
