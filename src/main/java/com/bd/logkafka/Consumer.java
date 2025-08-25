package com.bd.logkafka;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.bd.dto.CurrentDay;
import com.bd.dto.WeatherLocation;
import com.bd.dto.WeatherForecastDTO;
import com.bd.dto.WeatherLogDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class Consumer {
    ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void listen(String message) throws IOException {
        WeatherForecastDTO data = mapper.readValue(message, WeatherForecastDTO.class);
        String serverUrl = "http://localhost:9200";
        String apiKey = "RWNEd29KZ0JkeUVxOVdIM2xNYnk6dDY1TGM3dHQ3NGMzRnAyQlNpbS1TZw==";
        ElasticsearchClient esClient = ElasticsearchClient.of(b -> b
                .host(serverUrl)
                .apiKey(apiKey)
        );


        Random random = new Random();
        for (WeatherLocation location : data.getLocations()) {
            WeatherLogDto logDto = new WeatherLogDto();
            Map<String, Double> geoPoint = new HashMap<>();
            geoPoint.put("lat", location.getLatitude());
            geoPoint.put("lon", location.getLongitude());
            long id = random.nextLong();
            createDTO(location, logDto, id, geoPoint);
            System.out.println(logDto.getIndex());
            IndexResponse response = esClient.index(i -> i
                    .index(logDto.getIndex())
                    .id(String.valueOf(id))
                    .document(logDto)
            );
            System.out.println("Indexed: " + logDto.getIndex());
        }
        esClient.close();
    }

    private void createDTO(WeatherLocation location, WeatherLogDto logDto, long id, Map<String, Double> geoPoint) {
        CurrentDay currentDay = location.getDays().get(0);
        this.setLocationIndex(location, logDto);
        logDto.setId(id);
        logDto.setCityName(location.getResolvedAddress());
        logDto.setTimestamp(System.currentTimeMillis());
        logDto.setLocation(geoPoint);
        logDto.setFeelsLike(currentDay.getFeelslike());
        logDto.setCurrentTemperature(currentDay.getTemp());
    }

    private void setLocationIndex(WeatherLocation location, WeatherLogDto logDto) {
        Map<String, String> locationMap = new HashMap<>();
        locationMap.put("Cape Town, Western Cape, South Africa", "weather_logs_sa");
        locationMap.put("Tokyo, Japan", "weather_logs_japan");
        locationMap.put("Paris, Île-de-France, France", "weather_logs_france");
        locationMap.put("London, England, United Kingdom", "weather_logs_uk");
        logDto.setIndex(locationMap.get(location.getResolvedAddress()));
    }

}
