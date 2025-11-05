package com.bd.Client;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.bd.Environments;
import com.bd.dto.ForecastWeather;
import com.bd.dto.WeatherDto;
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

    public void indexData(WeatherDto weatherData, String index) throws IOException {
        System.out.println("Before Indexing: " + weatherData.getTimestamp() + " " + weatherData.getCityName());
        IndexResponse response = esClient.index(i -> i
                .index(index)
                .id(String.valueOf(weatherData.getId()))
                .document(weatherData)
        );
        System.out.println("Indexed: " + response.result().jsonValue());
    }

    protected void close() throws IOException {
        esClient.close();
    }

}
