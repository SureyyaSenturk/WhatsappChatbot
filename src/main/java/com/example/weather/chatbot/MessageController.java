package com.example.weather.chatbot;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MessageController {
    private final TwilioServiceImpl twilioService;

    public MessageController(TwilioServiceImpl twilioService) {
        this.twilioService = twilioService;

    }

    @PostMapping(value = "/webhook")
    public void handleWebhookRequest(@RequestBody MultiValueMap<String, String> requestBody) {
        twilioService.sendWeather(requestBody);
    }
}