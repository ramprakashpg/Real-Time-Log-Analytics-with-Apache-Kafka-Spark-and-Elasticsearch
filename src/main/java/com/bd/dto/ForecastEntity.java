package com.bd.dto;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WeatherForecast")
public class ForecastEntity extends MongoEntity{
}
