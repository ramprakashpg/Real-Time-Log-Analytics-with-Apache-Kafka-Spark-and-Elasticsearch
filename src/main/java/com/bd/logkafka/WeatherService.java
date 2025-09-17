package com.bd.logkafka;

import com.bd.dto.CurrentDay;
import com.bd.dto.WeatherLocation;
import com.bd.dto.WeatherLogDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


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
}
