package com.example.weatherservice.service;

import com.example.weatherservice.model.Weather;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApi {

    private static final Logger logger = LoggerFactory.getLogger(WeatherApi.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    public Weather getWeather(String city) throws Exception {
        String url = String.format("%s?q=%s&appid=%s", apiUrl, city, apiKey);
        logger.info("Fetching weather data from URL: {}", url);
        String response = restTemplate.getForObject(url, String.class);
        logger.info("Received raw weather data: {}", response);

        try {
            JsonNode root = objectMapper.readTree(response);
            Weather weather = new Weather();
            weather.setCity(root.path("name").asText());
            weather.setTemperature(root.path("main").path("temp").asText());
            weather.setDescription(root.path("weather").get(0).path("description").asText());
            logger.info("Converted weather data: {}", weather);
            return weather;
        } catch (Exception e) {
            logger.error("Error parsing weather data", e);
            throw e;
        }
    }
}
