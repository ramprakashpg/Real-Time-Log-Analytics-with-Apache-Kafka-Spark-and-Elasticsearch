package com.bd.Consumer;

import com.bd.Client.ESClient;
import com.bd.Client.MongoClient;
import com.bd.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CurrentWeatherConsumer {
    MongoClient mongoClient;
    ObjectMapper mapper = new ObjectMapper();

    WeatherService weatherService;
    ESClient esClient;


    //    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void listen(String message) throws IOException {
        JsonNode root = mapper.readTree(message);
        JsonNode locations = root.get("locations");
        for (int index = 0; index < locations.size(); index++) {
            JsonNode eachLocation = locations.get(index);
            weatherService.esIndexing(eachLocation);

        }
//        esClient.close();
    }

    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void mongoClient(String message) throws JsonProcessingException {
        mongoClient.forecastArchivePush(message);
    }
}
