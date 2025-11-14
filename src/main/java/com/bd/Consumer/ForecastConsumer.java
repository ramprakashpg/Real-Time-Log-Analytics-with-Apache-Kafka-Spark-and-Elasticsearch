package com.bd.Consumer;

import com.bd.Client.MongoClient;
import com.bd.Repository.WeatherRepository;
import com.bd.service.WeatherForecastService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ForecastConsumer {
    MongoClient mongoClient;
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    WeatherRepository weatherRepository;
    WeatherForecastService weatherForecastService;

//    @KafkaListener(topics = "weather_logs_forecast", groupId = "weather-forecast-group")
    private void listener(String message) throws IOException {
        JsonNode root = mapper.readTree(message);
        JsonNode locations = root.get("locations");
        for (int index = 0; index < locations.size(); index++) {
            JsonNode eachLocation = locations.get(index);
            weatherForecastService.esIndexingForecast(eachLocation);
        }
    }
    @KafkaListener(topics = "weather_logs_forecast", groupId = "weather-forecast-group")
    private void mongoClient(String message) throws JsonProcessingException {
        mongoClient.forecastArchivePush(message);
    }

}
