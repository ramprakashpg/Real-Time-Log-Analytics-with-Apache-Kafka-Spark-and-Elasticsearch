package com.bd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentWeather extends WeatherDto {
    public CurrentWeather() {
    }
    private double currentTemperature;
    private double feelsLike;
    private String countryCode;

}
