package com.bd.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Weather {
    @JsonProperty

    private int id;
    @JsonProperty
    private String main;
    @JsonProperty
    private String description;
    @JsonProperty
    private String icon;
}
