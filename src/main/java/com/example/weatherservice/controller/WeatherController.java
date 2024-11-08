package com.example.weatherservice.controller;
 
import com.example.weatherservice.model.ErrorResponse;
import com.example.weatherservice.model.Weather;
import com.example.weatherservice.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
 
@RestController
public class WeatherController {
 
    @Autowired
    private WeatherService weatherService;
 
    @GetMapping("/weather/{city}")
    public ResponseEntity<?> fetchAndSaveWeather(@PathVariable String city) {
        try {
            Weather weather = weatherService.fetchAndSaveWeather(city);
            return ResponseEntity.ok(weather);
        } catch (HttpClientErrorException.NotFound e) {
            // If the weather API returns a 404, handle it and return custom error response
            ErrorResponse errorResponse = new ErrorResponse("404", "City not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Handle any other exceptions
            ErrorResponse errorResponse = new ErrorResponse("500", "An error occurred while fetching weather data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
