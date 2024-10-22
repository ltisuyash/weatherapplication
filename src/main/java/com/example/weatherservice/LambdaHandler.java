package com.example.weatherservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.weatherservice.model.Weather;
import com.example.weatherservice.service.WeatherService;

@Component
public class LambdaHandler implements RequestHandler<Map<String, Object>, Weather> {

    private final WeatherService weatherService;

    @Autowired
    public LambdaHandler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public Weather handleRequest(Map<String, Object> input, Context context) {
        Object headersObj = input.get("headers");
        if (headersObj instanceof Map) {
            Map<?, ?> headersMap = (Map<?, ?>) headersObj;
            String city = (String) headersMap.get("city");
            return weatherService.fetchAndSaveWeather(city);
        } else {
            throw new IllegalArgumentException("Invalid headers format");
        }
    }
}
