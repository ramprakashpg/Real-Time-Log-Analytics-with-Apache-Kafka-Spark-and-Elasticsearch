package com.bd.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WindInfo {
    @JsonProperty

    private double speed;
    @JsonProperty

    private int deg;
    @JsonProperty

    private double gust;
}
