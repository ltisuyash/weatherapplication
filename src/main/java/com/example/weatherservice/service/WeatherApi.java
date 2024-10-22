package com.example.weatherservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.weatherservice.model.Weather;

@Service
public class WeatherApi {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    public Weather getWeather(String city) {
        String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);
        return restTemplate.getForObject(url, Weather.class);
    }
}
