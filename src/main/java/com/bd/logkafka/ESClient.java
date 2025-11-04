package com.bd.logkafka;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.bd.Environments;
import com.bd.dto.WeatherForecastDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ESClient {


    String serverUrl;
    String apiKey;
    ElasticsearchClient esClient;

    public ESClient() {
        this.apiKey = Environments.esApiKey.getValue();
        this.serverUrl = Environments.esUrl.getValue();
        this.esClient = ElasticsearchClient.of(b -> b
                .host(serverUrl)
                .apiKey(apiKey)
        );
    }

    protected void indexData(WeatherForecastDTO forecast) throws IOException {
        System.out.println("Before Indexing: "+forecast.getTimestamp()+" "+forecast.getCityName());
        IndexResponse response = esClient.index(i -> i
                .index("weather_forecast")
                .id(String.valueOf(forecast.getId()))
                .document(forecast)
        );
        System.out.println("Indexed: " + response.result().jsonValue());
    }

    protected void close() throws IOException {
        esClient.close();
    }

}
