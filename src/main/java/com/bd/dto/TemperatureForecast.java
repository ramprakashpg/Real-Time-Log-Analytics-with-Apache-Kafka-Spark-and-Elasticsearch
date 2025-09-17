package com.bd.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TemperatureForecast {
    private long timestamp;
    private double temp;
}
