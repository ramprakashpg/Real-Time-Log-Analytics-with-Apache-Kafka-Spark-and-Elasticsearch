package com.bd.logkafka;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.bd.Environments;
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
        String serverUrl = Environments.esUrl.getValue();
        String apiKey = Environments.esApiKey.getValue();
        ElasticsearchClient esClient = ElasticsearchClient.of(b -> b
                .host(serverUrl)
                .apiKey(apiKey)
        );


        Random random = new Random();
        for (WeatherLocation location : data.getLocations()) {
            WeatherLogDto logDto = new WeatherLogDto();

            long id = random.nextInt();
            esIndexing(location, logDto, id);
            IndexResponse response = esClient.index(i -> i
                    .index("weather_current")
                    .id(String.valueOf(id))
                    .document(logDto)
            );
            System.out.println("Indexed: " + response.index());
        }
        esClient.close();
    }

    private void esIndexing(WeatherLocation location, WeatherLogDto logDto, long id) {
        Map<String, Double> geoPoint = new HashMap<>();
        geoPoint.put("lat", location.getLatitude());
        geoPoint.put("lon", location.getLongitude());
        CurrentDay currentDay = location.getDays().get(0);
        logDto.setId(id);
        logDto.setCityName(location.getResolvedAddress());
        logDto.setTimestamp(System.currentTimeMillis());
        logDto.setLocation(geoPoint);
        logDto.setFeelsLike(currentDay.getFeelslike());
        logDto.setCurrentTemperature(currentDay.getTemp());
    }

    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void mongoClient(String message) {


    }

}
