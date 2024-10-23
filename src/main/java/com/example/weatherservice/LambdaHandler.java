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
            context.getLogger().log("Received input: " + input);
            String source = (String) input.get("source");
          /*  if ("aws.events".equals(source)) {
                // Handle EventBridge event
                context.getLogger().log("EventBridge event received");
                // Process the event as needed
                return null; // or return a default Weather object
            } else {*/
                // Handle API Gateway event
                Object headersObj = input.get("headers");
                if (headersObj instanceof Map) {
                    Map<?, ?> headersMap = (Map<?, ?>) headersObj;
                    context.getLogger().log("Headers map: " + headersMap);
                    headersMap.forEach((key, value) -> context.getLogger().log("Header: " + key + " = " + value));
                    String city = (String) headersMap.get("city");
                    if (city == null || city.isEmpty()) {
                        throw new IllegalArgumentException("City cannot be null or empty");
                    }
                    return weatherService.fetchAndSaveWeather(city);
					/*
					 * } else { context.getLogger().log("Invalid headers format: " + headersObj);
					 * throw new IllegalArgumentException("Invalid headers format"); }
					 */
            }
        } catch (Exception e) {
            // Log the error and return a meaningful response or rethrow the exception
            context.getLogger().log("Error processing request: " + e.getMessage());
            throw e;
        }
        return null; 
    }
}
