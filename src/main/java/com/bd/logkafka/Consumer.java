package com.bd.logkafka;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.apache.spark.sql.*;

import java.io.IOException;
import java.util.Collections;

@Component
public class Consumer {
    ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "weather-logs", groupId = "weather-group")
    public void listen(String message) throws IOException {
        WeatherDTO data = mapper.readValue(message, WeatherDTO.class);
        String serverUrl = "http://localhost:9200";
        String apiKey = "RWNEd29KZ0JkeUVxOVdIM2xNYnk6dDY1TGM3dHQ3NGMzRnAyQlNpbS1TZw==";
        ElasticsearchClient esClient = ElasticsearchClient.of(b -> b
                .host(serverUrl)
                .apiKey(apiKey)
        );
//        esClient.indices().create(c -> c
//                .index("weather-logs")
//        );
        Product product = new Product("bk-1", "City bike");

        IndexResponse response = esClient.index(i -> i
                .index("weather-logs")
                .id(product.getHello())
                .document(product)
        );
        System.out.println("Indexed"+response.version());
//        logger.info("Indexed with version " + response.version());
        esClient.close();
        System.out.println(message);
    }

}
