package com.bd.Consumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.bd.Environments;
import com.bd.dto.WeatherForecastDTO;
import com.bd.dto.WeatherLogDto;
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
    ObjectMapper mapper = new ObjectMapper();

    WeatherService weatherService;


    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void listen(String message) throws IOException {
        JsonNode root = mapper.readTree(message);
        JsonNode locations = root.get("locations");

//        WeatherForecastDTO data = mapper.readValue(message, WeatherForecastDTO.class);
        String serverUrl = Environments.esUrl.getValue();
        String apiKey = Environments.esApiKey.getValue();
        ElasticsearchClient esClient = ElasticsearchClient.of(b -> b
                .host(serverUrl)
                .apiKey(apiKey)
        );
        for (int index = 0; index < locations.size(); index++) {
            JsonNode eachLocation = locations.get(index);
            WeatherLogDto logDto = weatherService.esIndexing(eachLocation);
            IndexResponse response = esClient.index(i -> i
                    .index("weather_current")
                    .id(String.valueOf(logDto.getId()))
                    .document(logDto)
            );
            System.out.println("Indexed: " + eachLocation.get(""));
        }
        esClient.close();
    }


    //    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void mongoClient(String message) throws JsonProcessingException {
        WeatherForecastDTO data = mapper.readValue(message, WeatherForecastDTO.class);
        weatherService.indexRawData(data);
        System.out.println("Logged to Mongo...");

    }
}
