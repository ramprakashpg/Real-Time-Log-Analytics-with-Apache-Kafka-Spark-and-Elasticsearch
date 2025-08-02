package com.bd.logkafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Properties;

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
    private void sendData() {
        String message = getWeatherData();
        kafkaTemplate.send("weather-logs", message);
    }

//    public static void main(String[] args) {
//        Properties properties = new Properties();
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        properties.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        try (org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<>(properties)) {
//            String message = getWeatherData();
//            ProducerRecord<String, String> record = new ProducerRecord<>("weather-logs", "", message);
//            producer.send(record, new Callback() {
//                @Override
//                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                    if (e != null) {
//                        System.out.println(e.getMessage());
//                    } else {
//                        System.out.println("Message sent successfully");
//                    }
//                }
//            });
//        } catch (Exception e) {
//            System.out.println("Exception Occurred:" + e.getMessage());
//        }
//
//    }
}
