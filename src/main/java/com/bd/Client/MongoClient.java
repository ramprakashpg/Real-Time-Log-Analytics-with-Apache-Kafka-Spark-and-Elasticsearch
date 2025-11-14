package com.bd.Client;

import com.bd.dto.ForecastEntity;
import com.bd.dto.MongoEntity;
import com.bd.dto.WeatherEntity;
import com.bd.service.WeatherForecastService;
import com.bd.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.bson.Document;

import java.util.Random;

@Component
public class MongoClient {
    Random random = new Random();

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    WeatherService weatherService;
    @Autowired
    private WeatherForecastService weatherForecastService;

    public void archivePush(String message) throws JsonProcessingException {
        MongoEntity entity = new WeatherEntity();
        processData(message, entity);
        weatherService.indexRawData(entity);
        System.out.println("Current weather logged to Mongo...");

    }

    private void processData(String message, MongoEntity entity) {
        Document document = Document.parse(message);
        int id = random.nextInt();
        entity.setId(id);
        entity.setPayload(document);
    }

    public void forecastArchivePush(String message){
        MongoEntity entity = new ForecastEntity();
        processData(message, entity);
        weatherForecastService.indexRawData(entity);
        System.out.println("Forecast data logged to Mongo...");
    }
}
