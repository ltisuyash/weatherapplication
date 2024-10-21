package com.example.weatherservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.weatherservice.model.Weather;
import com.example.weatherservice.service.WeatherService;

@Component
public class LambdaHandler implements RequestHandler<Map<String, String>, Weather> {

    private WeatherService weatherService;

    public LambdaHandler() {
        this.weatherService = new WeatherService();
    }

    @Autowired
    public LambdaHandler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public Weather handleRequest(Map<String, String> input, Context context) {
        String city = input.get("city");
        return weatherService.getWeatherByCity(city);
    }
}
