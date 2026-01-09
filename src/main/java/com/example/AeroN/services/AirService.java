package com.example.AeroN.services;

import com.example.AeroN.OpenSkyData;
import com.example.AeroN.repositories.AirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirService {
    private AirRepository airRep;

    @Autowired
    public AirService(AirRepository airRep) {
        this.airRep = airRep;
    }
}
