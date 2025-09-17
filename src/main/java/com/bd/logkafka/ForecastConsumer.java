package com.bd.logkafka;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.bd.Environments;
import com.bd.dto.WeatherForecastDTO;
import com.bd.dto.WeatherLogDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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

//        WeatherForecastDTO data = mapper.readValue(message, WeatherForecastDTO.class);
        String serverUrl = Environments.esUrl.getValue();
        String apiKey = Environments.esApiKey.getValue();
        ElasticsearchClient esClient = ElasticsearchClient.of(b -> b
                .host(serverUrl)
                .apiKey(apiKey)
        );

        for (int index = 0; index < locations.size(); index++) {
            JsonNode eachLocation = locations.get(index);
            WeatherForecastDTO forecast = weatherService.esIndexingForecast(eachLocation);
            IndexResponse response = esClient.index(i -> i
                    .index("weather_forecast")
                    .id(String.valueOf(forecast.getId()))
                    .document(forecast)
            );
            System.out.println("Indexed: " + eachLocation.get("resolvedAddress"));
        }
        esClient.close();
    }


}
