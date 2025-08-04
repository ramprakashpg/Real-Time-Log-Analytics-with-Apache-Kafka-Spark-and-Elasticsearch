package com.bd.logkafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Main {
    @JsonProperty
    private double temp;
    @JsonProperty

    private double feels_like;
    @JsonProperty

    private double temp_min;
    @JsonProperty

    private double temp_max;
    @JsonProperty

    private int pressure;
    @JsonProperty

    private int humidity;
    @JsonProperty

    private int sea_level;
    @JsonProperty

    private int grnd_level;
}
