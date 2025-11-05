package com.bd.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
public abstract class WeatherDto {
    private long id;
    private Map<String, Double> location;
    private String cityName;
    private long timestamp;

}
