package com.example.weatherservice.repository.dynamodb;

import com.example.weatherservice.model.Weather;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface WeatherRepository extends CrudRepository<Weather, String> {
    Weather findByCity(String city);
}
