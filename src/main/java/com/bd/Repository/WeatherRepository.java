package com.bd.Repository;

import com.bd.dto.ForecastWeather;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends MongoRepository<ForecastWeather, Integer> {
}
