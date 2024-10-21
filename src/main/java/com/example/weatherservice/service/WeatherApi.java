package com.example.weatherservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.weatherservice.model.Weather;

@Service
public class WeatherApi {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String apiKey = "1122e5a53be72e7ced513e190d590ae1";

    public Weather getWeather(String city) {
        String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);
        return restTemplate.getForObject(url, Weather.class);
    }
}
