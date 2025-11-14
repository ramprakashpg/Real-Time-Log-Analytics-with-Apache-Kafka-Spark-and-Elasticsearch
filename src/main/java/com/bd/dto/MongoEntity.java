package com.bd.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public abstract class MongoEntity {
    @Id
    private Integer id;
    private org.bson.Document payload;
}
