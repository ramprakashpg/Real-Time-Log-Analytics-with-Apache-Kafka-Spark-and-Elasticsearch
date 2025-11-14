package com.bd.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "WeatherData")
public class WeatherEntity extends MongoEntity{
}
