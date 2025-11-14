package com.bd.service;

import com.bd.Repository.ForecastRepository;
import com.bd.dto.ForecastEntity;
import com.bd.dto.ForecastWeather;
import com.bd.Client.ESClient;
import com.bd.dto.MongoEntity;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;

@Component
@AllArgsConstructor
public class WeatherForecastService {

    @Autowired
    WeatherService weatherService;

    @Autowired
    ESClient esClient;
    @Autowired
    private ForecastRepository forecastRepository;


    public void esIndexingForecast(JsonNode location) throws IOException {
        Random random = new Random();
        ForecastWeather forecastData = new ForecastWeather();
        Map<String, Double> geoPoint = weatherService.getLocationMapping(location);
        forecastData.setCityName(location.get("resolvedAddress").asText());
        forecastData.setLocation(geoPoint);
        forecastData.setForecasted_at(System.currentTimeMillis());
        JsonNode timestamps = location.get("days").get(0).get("hours");
        for (int i = 0; i < timestamps.size(); i++) {
            int id = abs(random.nextInt());
            forecastData.setId(id);
            long time = timestamps.get(i).get("datetimeEpoch").asLong() * 1000;
            forecastData.setTemp(timestamps.get(i).get("temp").asDouble());
            forecastData.setTimestamp(time);
            esClient.indexData(forecastData, "weather_forecast");
        }
//        esClient.close();

    }

    public void indexRawData(MongoEntity data) {
        forecastRepository.save(data);
    }

}
