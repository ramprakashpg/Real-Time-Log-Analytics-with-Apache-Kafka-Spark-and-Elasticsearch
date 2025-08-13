package com.bd.logkafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
public class WeatherDTO {

    @JsonProperty("coord")
    private Coordinates coord;
    @JsonProperty
    private List<Weather> weather;
    @JsonProperty

    private String base;
    @JsonProperty

    private Main main;
    @JsonProperty("visibility")
    private int visibility;
    @JsonProperty

    private WindInfo wind;
    @JsonProperty
    private RainInfo rain;

    @JsonProperty
    private Cloudiness clouds;
    @JsonProperty

    private long dt;
    @JsonProperty

    private Sys sys;
    @JsonProperty

    private int timezone;
    @JsonProperty

    private long id;
    @JsonProperty

    private String name;
    @JsonProperty

    private int cod;


}
