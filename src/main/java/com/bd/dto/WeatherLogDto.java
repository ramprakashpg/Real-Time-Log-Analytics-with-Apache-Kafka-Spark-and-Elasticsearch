package com.bd.dto;

import co.elastic.clients.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherLogDto {
    private long id;
    private String index;
    private Map<String, Double> location;
    private long timestamp;
    private double currentTemperature;
    private double feelsLike;
    private String cityName;
    private String countryCode;
}
