package com.bd.logkafka;

import com.bd.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.*;


@Component
@Service
@AllArgsConstructor
@NoArgsConstructor
public class WeatherService {
    Random random = new Random();

    public WeatherLogDto esIndexing(JsonNode location) {
        WeatherLogDto logDto = new WeatherLogDto();

        long id = random.nextInt();
        Map<String, Double> geoPoint = new HashMap<>();
        geoPoint.put("lat", location.get("latitude").asDouble());
        geoPoint.put("lon", location.get("longitude").asDouble());
        logDto.setId(id);
        logDto.setCityName(location.get("resolvedAddress").asText());
        logDto.setTimestamp(System.currentTimeMillis());
        logDto.setLocation(geoPoint);
        logDto.setFeelsLike(location.get("days").get(0).get("feelslike").asDouble());
        logDto.setCurrentTemperature(location.get("days").get(0).get("temp").asDouble());
        return logDto;
    }

    public WeatherForecastDTO esIndexingForecast(JsonNode location) {
        WeatherForecastDTO forecastData = new WeatherForecastDTO();
        int id = random.nextInt();
        Map<String, Double> geoPoint = new HashMap<>();
        geoPoint.put("lat", location.get("latitude").asDouble());
        geoPoint.put("lon", location.get("longitude").asDouble());
        forecastData.setId(id);
        forecastData.setCityName(location.get("resolvedAddress").asText());
        forecastData.setLocation(geoPoint);
        forecastData.setForecasted_at(System.currentTimeMillis());
        List<TemperatureForecast> temperatureForecastList = setTemperature(location.get("days").get(0).get("hours"));
        forecastData.setTemperature(temperatureForecastList);
        return forecastData;

    }

    public List<TemperatureForecast> setTemperature(JsonNode timestamps) {
        List<TemperatureForecast> temperatureForecastList = new ArrayList<>();
        for (int i = 0; i < timestamps.size(); i++) {
            TemperatureForecast forecast = new TemperatureForecast();
            LocalTime time = LocalTime.parse(timestamps.get(i).get("datetimeEpoch").asText());
            long seconds = time.toSecondOfDay() * 1000L;
            System.out.println(timestamps.get(i).get("datetimeEpoch").asLong());
            forecast.setTimestamp(timestamps.get(i).get("datetimeEpoch").asLong());
            forecast.setTemp(timestamps.get(i).get("temp").asDouble());
            System.out.println(System.currentTimeMillis());
            temperatureForecastList.add(forecast);
        }
        return temperatureForecastList;
    }
}
