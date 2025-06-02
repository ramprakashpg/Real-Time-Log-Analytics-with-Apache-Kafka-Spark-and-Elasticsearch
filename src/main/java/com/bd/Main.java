package com.bd;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Main {
    public static volatile Main main;

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<>(properties);
            ProducerRecord<String, String> record = new ProducerRecord<>("Messages", "key", "value");
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        System.out.println(e.getMessage());
                    } else {
                        System.out.println("Message sent successfully");
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Exception Occurred:" + e.getMessage());
        } finally {
            if (producer != null) {
                producer.close();
            }
        }

    }
}