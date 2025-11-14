package com.bd.Repository;

import com.bd.dto.ForecastEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastRepository extends MongoRepository<ForecastEntity, Integer> {
}
