package com.bd.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
public class ForecastWeather extends WeatherDto{
    private long forecasted_at;
    private double temp;
}
