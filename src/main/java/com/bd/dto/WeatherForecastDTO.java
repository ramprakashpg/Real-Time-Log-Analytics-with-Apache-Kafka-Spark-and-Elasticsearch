package com.bd.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class WeatherForecastDTO {
    private int queryCost;
    private List<Location1> locations;
}
