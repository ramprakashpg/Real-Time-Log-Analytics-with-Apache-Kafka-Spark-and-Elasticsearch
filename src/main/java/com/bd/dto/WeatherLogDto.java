package com.bd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class WeatherLogDto {
    public WeatherLogDto(){
    }

    private long id;
    private String index;
    private Map<String, Double> location;
    private long timestamp;
    private double currentTemperature;
    private double feelsLike;
    private String cityName;
    private String countryCode;

}
