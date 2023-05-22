package com.example.weather.chatbot;

import org.springframework.util.MultiValueMap;

public interface TwillioService {

    void sendWeather(MultiValueMap<String, String> requestBody);
}
