package com.example.weather.chatbot.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {
    private String version;
    private String user;
    private String dateGenerated;
    private String status;

    private List<WeatherData> data;

}
