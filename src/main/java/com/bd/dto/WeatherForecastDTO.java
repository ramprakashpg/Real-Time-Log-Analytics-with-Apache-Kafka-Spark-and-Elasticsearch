package com.bd.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Document("WeatherData")
public class WeatherForecastDTO {
    private int queryCost;
    private List<WeatherLocation> locations;
}
