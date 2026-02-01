package com.example.AeroN.config;

import com.example.AeroN.AirData;
import com.example.AeroN.enteties.AirEntity;
import com.example.AeroN.enteties.ConditionEntity;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        Converter<Integer, LocalDateTime> secondsToLocalDateTime = new AbstractConverter<Integer, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(Integer seconds) {
                return seconds != null
                        ? LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.of("UTC"))
                        : null;
            }
        };

        // Применяем конвертер только к типу SourceClass → DestinationClass
        // и только для поля lastContact
        modelMapper.createTypeMap(AirData.class, ConditionEntity.class)
                .addMappings(mapper -> {
                    mapper.using(secondsToLocalDateTime)
                            .map(AirData::getLastContact, ConditionEntity::setLastContact);
                });
        return modelMapper;
    }
}
