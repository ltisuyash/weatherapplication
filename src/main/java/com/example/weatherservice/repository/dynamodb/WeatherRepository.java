package com.example.weatherservice.repository.dynamodb;


import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import com.example.weatherservice.model.Weather;

@EnableScan
public interface WeatherRepository extends CrudRepository<Weather, String> {
}

