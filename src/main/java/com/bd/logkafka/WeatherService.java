package com.bd.logkafka;

import com.bd.dto.WeatherForecastDTO;
import com.bd.dto.WeatherLogDto;
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


@Component
@Service
@AllArgsConstructor
@NoArgsConstructor
public class WeatherService {
    Random random = new Random();
    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    ESClient esClient;


    public WeatherLogDto esIndexing(JsonNode location) {
        WeatherLogDto logDto = new WeatherLogDto();

        long id = random.nextInt();
        Map<String, Double> geoPoint = getLocationMapping(location);
        logDto.setId(id);
        logDto.setCityName(location.get("resolvedAddress").asText());
        logDto.setTimestamp(System.currentTimeMillis());
        logDto.setLocation(geoPoint);
        logDto.setFeelsLike(location.get("days").get(0).get("feelslike").asDouble());
        logDto.setCurrentTemperature(location.get("days").get(0).get("temp").asDouble());
        return logDto;
    }

    private @NotNull Map<String, Double> getLocationMapping(JsonNode location) {
        Map<String, Double> geoPoint = new HashMap<>();
        geoPoint.put("lat", location.get("latitude").asDouble());
        geoPoint.put("lon", location.get("longitude").asDouble());
        return geoPoint;
    }

    public void esIndexingForecast(JsonNode location) throws IOException {
        WeatherForecastDTO forecastData = new WeatherForecastDTO();
        Map<String, Double> geoPoint = getLocationMapping(location);
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
            esClient.indexData(forecastData);
        }
//        esClient.close();

    }

    private void setTemperature(String time, WeatherForecastDTO forecastData, String date) {
//        String datetime = date + "T" + time;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//
//        LocalDateTime ldt = LocalDateTime.parse(datetime, formatter);
//        System.out.println(ldt);
//        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));

    }

    public void indexRawData(WeatherForecastDTO data) {
        weatherRepository.save(data);
    }
}


