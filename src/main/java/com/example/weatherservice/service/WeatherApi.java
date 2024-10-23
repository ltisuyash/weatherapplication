package com.example.weatherservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.weatherservice.model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherApi {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApi.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    public Weather getWeather(String city) {
        String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);
        logger.info("Fetching weather data from URL: {}", url);
        String response = restTemplate.getForObject(url, String.class);
        logger.info("Received raw weather data: {}", response);

        // Convert the raw response to Weather object
        Weather weather = restTemplate.getForObject(url, Weather.class);
        logger.info("Converted weather data: {}", weather);
        return weather;
    }
}
