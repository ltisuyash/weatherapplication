package com.example.weatherservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.weatherservice.model.Weather;
import com.example.weatherservice.service.WeatherService;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather/{city}")
    public Weather fetchAndSaveWeather(@PathVariable String city) throws Exception {
        try {
            return weatherService.fetchAndSaveWeather(city);
        } catch (Exception e) {
            throw e;
        }
    }
}
