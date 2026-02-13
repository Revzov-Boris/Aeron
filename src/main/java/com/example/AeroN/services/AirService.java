package com.example.AeroN.services;

import com.example.AeroN.dto.AirView;
import com.example.AeroN.enteties.AirEntity;
import com.example.AeroN.repositories.AirRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AirService {
    private AirRepository airRep;
    private final ModelMapper modelMapper;


    @Autowired
    public AirService(AirRepository airRep, ModelMapper mp) {
        this.airRep = airRep;
        this.modelMapper = mp;
    }


    public List<AirView> getAllView() {
        System.out.println("обращение к /map");
        int minutes = 195;
        LocalDateTime lastTime = LocalDateTime.now().minusMinutes(minutes);
        List<AirEntity> allAirs = airRep.findLastOnLastMinut(lastTime);

        List<AirView> views = new ArrayList<>();
        for (AirEntity entity : allAirs) {
            System.out.println(entity.getConditions().size());
            if (entity.getConditions().size() > 0) {
                AirView view = modelMapper.map(entity.getConditions().getLast(), AirView.class);
                view.setIcao24(entity.getIcao24());
                view.setCountry(entity.getCountry());
                if (entity.getCategory() != null) {
                    view.setCategory(entity.getCategory().toString());
                }
                views.add(view);
            }
        }
        return views;
    }
}
