package com.bd.logkafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class WeatherDTO implements Serializable {

    private Coordinates coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private WindInfo wind;
    private RainInfo rain;
    private Cloudiness clouds;
    private int dt;
    private System sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;




}
