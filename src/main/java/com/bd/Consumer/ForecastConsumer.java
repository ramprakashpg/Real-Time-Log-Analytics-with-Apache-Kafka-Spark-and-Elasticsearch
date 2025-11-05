package com.bd.Consumer;

import com.bd.Repository.WeatherRepository;
import com.bd.service.WeatherService;
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
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    WeatherRepository weatherRepository;
    WeatherService weatherService;

    @KafkaListener(topics = "weather_logs_forecast", groupId = "weather-forecast-group")
    private void listener(String message) throws IOException {
        JsonNode root = mapper.readTree(message);
        JsonNode locations = root.get("locations");
        for (int index = 0; index < locations.size(); index++) {
            JsonNode eachLocation = locations.get(index);
            weatherService.esIndexingForecast(eachLocation);
        }
    }

}
