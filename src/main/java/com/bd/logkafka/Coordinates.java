package com.bd.logkafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

@NoArgsConstructor
@Getter
@Setter
public class Coordinates {
    @JsonProperty("lon")
    private double lon;
    @JsonProperty("lat")
    private double lat;
}
