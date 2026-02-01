package com.example.AeroN.services;

import com.example.AeroN.AirData;
import com.example.AeroN.OpenSkyData;
import com.example.AeroN.enteties.AirEntity;
import com.example.AeroN.enteties.ConditionEntity;
import com.example.AeroN.repositories.AirRepository;
import com.example.AeroN.repositories.ConditionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AirCollectorService {
    @Value("${openskynetwork.api.all}")
    private String apiUrl;
    private RestTemplate restTemplate;
    private AirRepository airRepository;
    private ConditionRepository conditionRepositry;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ModelMapper modelMapper;

    @Autowired
    public AirCollectorService(RestTemplate restTemplate, AirRepository airRep, ConditionRepository conditionRep, ModelMapper mp) {
        this.restTemplate = restTemplate;
        this.airRepository = airRep;
        this.conditionRepositry = conditionRep;
        this.modelMapper = mp;
    }

    // Запускается каждые 20 сек.
    @Scheduled(fixedRate = 30 * 1000)
    public void loadData() {
        try {
            OpenSkyData data = getData();
            if (data != null) {
                System.out.println("данные получены");
                List<AirEntity> airEntities = parseToAirEntities(data);
                saveNewAirs(airEntities);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public OpenSkyData getData() {
        String stringResult = restTemplate.getForObject(apiUrl, String.class);
        System.out.println(stringResult.substring(0, 700));
        try {
            JsonNode jsonNode = mapper.readTree(stringResult);
            OpenSkyData openSkyData = new OpenSkyData();
            List<AirData> airDataList = new ArrayList<>();
            openSkyData.setTime(jsonNode.get("time").asInt());
            for (JsonNode jn : jsonNode.path("states")) {
                AirData airData = parseToAirData(jn);
                if (airData.getOriginCountry().equals("Russian Federation")) {
                    airDataList.add(airData);
                }

            }
            openSkyData.setAirDataList(airDataList);
            return openSkyData;
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка json парсинга");
        }
        return null;
    }


    public AirData parseToAirData(JsonNode node) {
        AirData airData = new AirData();
        airData.setIcao24(node.get(0).asText(null));
        airData.setCallsign(node.get(1).asText(null));
        airData.setOriginCountry(node.get(2).asText(null));
        airData.setTimePosition(node.get(3).asInt());
        airData.setLastContact(node.get(4).asInt());
        airData.setLongitude(node.get(5).asDouble());
        airData.setLatitude(node.get(6).asDouble());
        airData.setBaroAltitude(node.get(7).asDouble());
        airData.setOnGround(node.get(8).asBoolean());
        airData.setVelocity(node.get(9).asDouble());
        airData.setTrueTrack(node.get(10).asDouble());
        airData.setVerticalRate(node.get(11).asDouble());
        airData.setGeoAltitude(node.get(13).asDouble());
        airData.setSquawk(node.get(14).asText());
        airData.setSpi(node.get(15).asBoolean());
        airData.setPositionSource(node.get(16).asInt());
        if (node.size() > 17) {
            airData.setCategory(node.get(17).asInt());
        }
        return airData;
    }


    public List<AirEntity> parseToAirEntities(OpenSkyData data) {
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(data.getTime(), 0, ZoneOffset.UTC);
        List<AirEntity> toAdd = new ArrayList<>();
        for (AirData airData : data.getAirDataList()) {
            ConditionEntity entity = modelMapper.map(airData, ConditionEntity.class);
            entity.setTime(dateTime);
            AirEntity airEntity = new AirEntity();
            airEntity.setIcao24(airData.getIcao24());
            airEntity.setCountry(airData.getOriginCountry());
            airEntity.setCategory(airData.getCategory());
            airEntity.setConditions(List.of(entity));
            entity.setAir(airEntity);
            toAdd.add(airEntity);
        }
        return toAdd;
    }


    public void saveNewAirs(List<AirEntity> airs) {
        Set<String> allIsao24 = airRepository.findAllIsao24();
        List<AirEntity> airsToAdd = new ArrayList<>();
        List<ConditionEntity> conditionsToAdd = new ArrayList<>();
        for (AirEntity air : airs) {
            if (!allIsao24.contains(air.getIcao24())) {
                airsToAdd.add(air);
            } else {
                AirEntity airEntity = airRepository.findByIsao24(air.getIcao24());
                ConditionEntity conditionEntity = air.getConditions().getFirst();
                conditionEntity.setAir(airEntity);
                conditionsToAdd.add(conditionEntity);
            }
        }
        airRepository.saveAll(airsToAdd);
        conditionRepositry.saveAll(conditionsToAdd);
    }

}
