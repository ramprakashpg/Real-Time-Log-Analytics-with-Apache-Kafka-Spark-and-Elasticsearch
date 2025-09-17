package com.bd.logkafka;

import com.bd.Environments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ForecastProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final WebClient webClient;

    public ForecastProducer(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services").build();
    }

    private String getWeatherData() {
        System.out.println("Fetching weather...");
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        System.out.println(currentDateAndTime);
        String currentDate = currentDateAndTime.format(formatter);
//        String elements = "temp,feelslike";
        String locations = "London,UK|Paris,France|Tokyo,Japan|Cape Town,South Africa";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/timelinemulti")
                        .queryParam("key", Environments.weatherApi.getValue())
                        .queryParam("locations", locations)
                        .queryParam("datestart", currentDate)
                        .queryParam("include","hours")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    @Scheduled(fixedRate = 6000)
    private void publish(){
        String weatherData = getWeatherData();
        kafkaTemplate.send("weather_logs_forecast",weatherData);
    }
}