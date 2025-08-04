package com.bd.logkafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void listen(String message) throws JsonProcessingException {
        WeatherDTO data = mapper.readValue(message, WeatherDTO.class);
    }
}
