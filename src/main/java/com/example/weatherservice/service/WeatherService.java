package com.example.weatherservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.weatherservice.model.Weather;
import com.example.weatherservice.repository.dynamodb.WeatherRepository;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherApi weatherApi;

    public Weather fetchAndSaveWeather(String city) throws Exception {
        try {
            // Fetch weather data from the external API
            Weather weather = weatherApi.getWeather(city);
            if (weather == null || weather.getCity() == null || weather.getCity().isEmpty()) {
                logger.error("Invalid weather data received from API: {}", weather);
                throw new IllegalArgumentException("Invalid weather data received from API");
            }
            // Save the weather data to DynamoDB
            weatherRepository.save(weather);
            return weather;

        } catch (Exception e) {
            throw e;
        }
    }
}
