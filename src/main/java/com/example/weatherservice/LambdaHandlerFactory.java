package com.example.weatherservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.weatherservice.service.WeatherService;

@Component
public class LambdaHandlerFactory {

    @Autowired
    private WeatherService weatherService;

    public LambdaHandler createHandler() {
        return new LambdaHandler(weatherService);
    }
}
