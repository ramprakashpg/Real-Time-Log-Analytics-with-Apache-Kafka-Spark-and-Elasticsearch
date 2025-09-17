package com.bd.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
public class WeatherForecastDTO {
    private int id;
    private long forecasted_at;
    private Map<String, Double> location;
    private String cityName;
    private List<TemperatureForecast> temperature;
}
