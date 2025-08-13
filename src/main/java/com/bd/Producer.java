package com.bd;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Collections;
import java.util.Properties;

public class Producer {
    public static void main(String[] args) {
        String message = "{\"coord\":{\"lon\":10.99,\"lat\":44.34},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"base\":\"stations\",\"main\":{\"temp\":289.61,\"feels_like\":289.66,\"temp_min\":289.41,\"temp_max\":289.94,\"pressure\":1021,\"humidity\":90,\"sea_level\":1021,\"grnd_level\":955},\"visibility\":10000,\"wind\":{\"speed\":2.12,\"deg\":193,\"gust\":1.8},\"rain\":{\"1h\":0.7},\"clouds\":{\"all\":95},\"dt\":1754524500,\"sys\":{\"type\":2,\"id\":2004688,\"country\":\"IT\",\"sunrise\":1754539799,\"sunset\":1754591620},\"timezone\":7200,\"id\":3163858,\"name\":\"Zocca\",\"cod\":200}\n";
        SparkSession spark = SparkSession.builder().appName("SimpleApp").master("local").getOrCreate();
        Dataset<String> weatherLog = spark.createDataset(Collections.singletonList(message), Encoders.STRING());
        Dataset<Row> df = spark.read().json(weatherLog);
        df.show();

//        Properties properties = new Properties();
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        org.apache.kafka.clients.producer.Producer<String, String> producer = null;
//        try {
//            producer = new KafkaProducer<>(properties);
//            ProducerRecord<String, String> record = new ProducerRecord<>("Messages", "key", "value");
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
//        } finally {
//            if (producer != null) {
//                producer.close();
//            }
//        }

    }


}
