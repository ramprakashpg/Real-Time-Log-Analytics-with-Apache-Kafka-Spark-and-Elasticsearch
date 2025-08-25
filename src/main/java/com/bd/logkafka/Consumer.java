package com.bd.logkafka;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.bd.dto.CurrentDay;
import com.bd.dto.Location1;
import com.bd.dto.WeatherForecastDTO;
import com.bd.dto.WeatherLogDto;
import com.bd.dto.weather.Sys;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class Consumer {
    ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void listen(String message) throws IOException {
        WeatherForecastDTO data = mapper.readValue(message, WeatherForecastDTO.class);
        String serverUrl = "http://localhost:9200";
        String apiKey = "RWNEd29KZ0JkeUVxOVdIM2xNYnk6dDY1TGM3dHQ3NGMzRnAyQlNpbS1TZw==";
        ElasticsearchClient esClient = ElasticsearchClient.of(b -> b
                .host(serverUrl)
                .apiKey(apiKey)
        );


        Random random = new Random();
        for (Location1 location : data.getLocations()) {
            WeatherLogDto logDto = new WeatherLogDto();
            Map<String, Double> geoPoint = new HashMap<>();
            geoPoint.put("lat", location.getLatitude());
            geoPoint.put("lon", location.getLongitude());
            long id = random.nextLong();
            createDTO(location, logDto, id, geoPoint);

            IndexResponse response = esClient.index(i -> i
                    .index("weather_logs[" +location.getTzoffset()+"]")
                    .id(String.valueOf(id))
                    .document(logDto)
            );
            System.out.println("Indexed" + response.version());
        }
        esClient.close();
    }

    private void createDTO(Location1 location, WeatherLogDto logDto, long id, Map<String, Double> geoPoint) {
        CurrentDay currentDay = location.getDays().get(0);
//        logDto.setIndex("weather_logs"+);
        logDto.setId(id);
        logDto.setCityName(location.getResolvedAddress());
        logDto.setTimestamp(System.currentTimeMillis());
        logDto.setLocation(geoPoint);
        logDto.setFeelsLike(currentDay.getFeelslike());
        logDto.setCurrentTemperature(currentDay.getTemp());
    }

}
