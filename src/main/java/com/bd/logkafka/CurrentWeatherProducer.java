package com.bd.logkafka;

import com.bd.Environments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.reactive.function.client.WebClient;


@Component
public class CurrentWeatherProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'H:mm:ss");

    private final WebClient webClient;

    public CurrentWeatherProducer(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services").build();
    }


    private String getWeatherData() {
        System.out.println("Fetching weather...");
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        String currentDate = currentDateAndTime.format(formatter);
//        String elements = "temp,feelslike";
        String locations = "London,UK|Paris,France|Tokyo,Japan|Cape Town,South Africa";
//        String dummyResponse = "{\n" +
//                "    \"queryCost\": 4,\n" +
//                "    \"locations\": [\n" +
//                "        {\n" +
//                "            \"queryCost\": 1,\n" +
//                "            \"latitude\": 51.5064,\n" +
//                "            \"longitude\": -0.12719,\n" +
//                "            \"resolvedAddress\": \"London, England, United Kingdom\",\n" +
//                "            \"address\": \"London,UK\",\n" +
//                "            \"timezone\": \"Europe/London\",\n" +
//                "            \"tzoffset\": 1.0,\n" +
//                "            \"days\": [\n" +
//                "                {\n" +
//                "                    \"temp\": 66.7,\n" +
//                "                    \"feelslike\": 66.7\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"queryCost\": 1,\n" +
//                "            \"latitude\": 48.8572,\n" +
//                "            \"longitude\": 2.34141,\n" +
//                "            \"resolvedAddress\": \"Paris, Île-de-France, France\",\n" +
//                "            \"address\": \"Paris,France\",\n" +
//                "            \"timezone\": \"Europe/Paris\",\n" +
//                "            \"tzoffset\": 2.0,\n" +
//                "            \"days\": [\n" +
//                "                {\n" +
//                "                    \"temp\": 72.2,\n" +
//                "                    \"feelslike\": 72.1\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"queryCost\": 1,\n" +
//                "            \"latitude\": 35.6884,\n" +
//                "            \"longitude\": 139.69,\n" +
//                "            \"resolvedAddress\": \"Tokyo, Japan\",\n" +
//                "            \"address\": \"Tokyo,Japan\",\n" +
//                "            \"timezone\": \"Asia/Tokyo\",\n" +
//                "            \"tzoffset\": 9.0,\n" +
//                "            \"days\": [\n" +
//                "                {\n" +
//                "                    \"temp\": 87.5,\n" +
//                "                    \"feelslike\": 96.6\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"queryCost\": 1,\n" +
//                "            \"latitude\": -33.9191,\n" +
//                "            \"longitude\": 18.422,\n" +
//                "            \"resolvedAddress\": \"Cape Town, Western Cape, South Africa\",\n" +
//                "            \"address\": \"Cape Town, South Africa\",\n" +
//                "            \"timezone\": \"Africa/Johannesburg\",\n" +
//                "            \"tzoffset\": 2.0,\n" +
//                "            \"days\": [\n" +
//                "                {\n" +
//                "                    \"temp\": 56.3,\n" +
//                "                    \"feelslike\": 56.3\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//        return dummyResponse;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/timelinemulti")
                        .queryParam("key", Environments.weatherApi.getValue())
                        .queryParam("locations", locations)
                        .queryParam("datestart", currentDate)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

//    @Scheduled(fixedRate = 6000)
    private void sendData() throws IOException {
        String message = getWeatherData();
        kafkaTemplate.send("weather-logs", message);
    }

}
