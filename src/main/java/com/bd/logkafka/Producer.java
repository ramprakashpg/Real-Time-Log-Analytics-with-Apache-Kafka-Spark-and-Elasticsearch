package com.bd.logkafka;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class Producer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static String getWeatherData() {
        System.out.println("Fetching weather...");
        String apiKey = "f20d9d0e7689817cc6be2d895054e464";
        String uri = "https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=f20d9d0e7689817cc6be2d895054e464";
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.getForEntity(uri, String.class);
        return response.getBody();
    }

    @Scheduled(fixedRate = 6000)
    private void sendData() throws IOException {
        String message = getWeatherData();
        kafkaTemplate.send("weather-logs", message);
    }

}
