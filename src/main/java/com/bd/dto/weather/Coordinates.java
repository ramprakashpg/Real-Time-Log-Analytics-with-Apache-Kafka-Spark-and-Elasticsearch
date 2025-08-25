package com.bd.dto.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class Coordinates {
    @JsonProperty("lon")
    private double lon;
    @JsonProperty("lat")
    private double lat;
    public Map<String, Double> toGeoPointMap() {
        Map<String, Double> geoPoint = new HashMap<>();
        geoPoint.put("lat", this.lat);
        geoPoint.put("lon", this.lon);
        return geoPoint;
    }
}
