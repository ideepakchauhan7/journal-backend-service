package net.engineeringDigestt.JournalApp.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WeatherResponse {

    private Current current;



    @Getter
    @Setter


    public class Current{

        @JsonProperty("observation_Time")
        private String observationTime;
        private int temperature;
        private int weather_code;

    }






}






