package com.example.weatherservice.controller;

import com.example.weatherservice.model.Weather;
import com.example.weatherservice.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public Weather getWeather(@PathVariable String city) {
        return weatherService.getWeatherByCity(city);
    }

    @PostMapping("/")
    public Weather saveWeather(@RequestBody Weather weather) {
        return weatherService.saveWeather(weather);
    }

    @GetMapping("/fetch-and-save/{city}")
    public Weather fetchAndSaveWeather(@PathVariable String city) {
        return weatherService.fetchAndSaveWeather(city);
    }
}
