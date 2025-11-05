package com.bd.service;

import com.bd.dto.ForecastWeather;
import com.bd.dto.CurrentWeather;
import com.bd.Client.ESClient;
import com.bd.Repository.WeatherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Service
public class WeatherService {
    Random random = new Random();
    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    ESClient esClient;


    public void esIndexing(JsonNode location) throws IOException {
        CurrentWeather logDto = new CurrentWeather();

        long id = random.nextInt();
        Map<String, Double> geoPoint = getLocationMapping(location);
        logDto.setId(id);
        logDto.setCityName(location.get("resolvedAddress").asText());
        logDto.setTimestamp(System.currentTimeMillis());
        logDto.setLocation(geoPoint);
        logDto.setFeelsLike(location.get("days").get(0).get("feelslike").asDouble());
        logDto.setCurrentTemperature(location.get("days").get(0).get("temp").asDouble());
        esClient.indexData(logDto, "weather_current");
    }

    protected @NotNull Map<String, Double> getLocationMapping(JsonNode location) {
        Map<String, Double> geoPoint = new HashMap<>();
        geoPoint.put("lat", location.get("latitude").asDouble());
        geoPoint.put("lon", location.get("longitude").asDouble());
        return geoPoint;
    }

    public void indexRawData(ForecastWeather data) {
        weatherRepository.save(data);
    }
}


