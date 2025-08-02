package com.bd.logkafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Component
public class Consumer {

    ObjectMapper mapper = new ObjectMapper();
    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void listen(String message) throws JsonProcessingException {
        WeatherDTO data = mapper.readValue(message, WeatherDTO.class);
        System.out.println(data.getId());
        System.out.println("Received: " + message);
    }

//    public static void main(String[] args) {
//        ObjectMapper mapper = new ObjectMapper();
//        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group-id");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//
//
//        try (org.apache.kafka.clients.consumer.Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
//            consumer.subscribe(List.of("weather-logs"));
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//
//                for (ConsumerRecord<String, String> record : records) {
////                    WeatherDTO data = mapper.readValue(record.value(), WeatherDTO.class);
////                    System.out.println(data.getMessage());
//                    System.out.println("Key: " + record.key() +
//                            " Value: " + record.value() +
//                            " Partition: " + record.partition() +
//                            " Offset: " + record.offset()
//                    );
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//
//    }
}
