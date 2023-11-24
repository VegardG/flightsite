package org.example.flighttrackingservice.service;

import org.example.flighttrackingservice.model.FlightData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class FlightTrackerService {
    private static final Logger logger = LoggerFactory.getLogger(FlightTrackerService.class);
    @Value("${airlabs.api.key}")
    private String apiKey;

    @Value("${airlabs.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public FlightTrackerService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public FlightData getFlightData(String flightNumber) {
        String url = apiUrl +  "/flights?flight_iata=" + flightNumber + "&api_key=" + apiKey;
        try {
                String rawResponse = restTemplate.getForObject(url, String.class);
                logger.info("API Response: {}", rawResponse);
                return restTemplate.getForObject(url, FlightData.class);
        } catch (Exception e) {
            logger.error("Error getting flight data:", e.getMessage(), e);
            return null;
        }
    }
}