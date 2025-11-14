package com.bd.Repository;

import com.bd.dto.ForecastEntity;
import com.bd.dto.MongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastRepository extends MongoRepository<MongoEntity, Integer> {
}
