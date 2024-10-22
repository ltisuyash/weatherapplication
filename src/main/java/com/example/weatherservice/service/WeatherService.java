package com.example.weatherservice.service;

import com.example.weatherservice.model.Weather;
import com.example.weatherservice.repository.dynamodb.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherApi weatherApi;

    public Weather fetchAndSaveWeather(String city) {
        Weather weather = weatherApi.getWeather(city);
        if (weather != null) {
            logger.info("Saving weather: {}", weather);
            weatherRepository.save(weather);
        }
        return weather;
    }
}
