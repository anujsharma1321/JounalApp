package com.firstProgram.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherResponse {
        private Current current;
    @Getter
    @Setter
    public class Current {
        @JsonProperty("temp_c")
        private Double tempC;

        @JsonProperty("temp_f")
        private Double tempF;


        @JsonProperty("wind_mph")
        private Double windMph;

        @JsonProperty("wind_kph")
        private Double windKph;

    }
}