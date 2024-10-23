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

    @Autowired
    private WeatherService weatherService;

    public LambdaHandler() {
        // Default constructor required by AWS Lambda
    }

    @Override
    public Weather handleRequest(Map<String, Object> input, Context context) {
        try {
            String source = (String) input.get("source");
            if ("aws.events".equals(source)) {
                // Handle EventBridge event
                return null; // or return a default Weather object
            } else {
                // Handle API Gateway event
                Object headersObj = input.get("headers");
                if (headersObj instanceof Map) {
                    Map<?, ?> headersMap = (Map<?, ?>) headersObj;
                    String city = (String) headersMap.get("city");
                    if (city == null || city.isEmpty()) {
                        throw new IllegalArgumentException("City cannot be null or empty");
                    }
                    return weatherService.fetchAndSaveWeather(city);
                } else {
                    throw new IllegalArgumentException("Invalid headers format");
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
