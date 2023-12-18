package org.example.flighttrackingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.flighttrackingservice.model.ApiResponse;
import org.example.flighttrackingservice.model.FlightData;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FlightTrackerService {
    private static final Logger logger = LoggerFactory.getLogger(FlightTrackerService.class);
    @Value("${airlabs.api.key}")
    private String apiKey;

    @Value("${airlabs.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FlightTrackerService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public List<FlightData> getFlightData(String flightNumber) {
        String url = apiUrl +  "/flights?flight_icao=" + flightNumber + "&api_key=" + apiKey;
        try {
            String rawResponse = restTemplate.getForObject(url, String.class);
            logger.info("API Response: {}", rawResponse);

            ApiResponse apiResponse = objectMapper.readValue(rawResponse, ApiResponse.class);
            return apiResponse.getFlights();
        } catch (Exception e) {
            logger.error("Error getting flight data: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<FlightData> getAllFlights(List<String> aircraftModel) {
        String url = apiUrl + "/flights?api_key=" + apiKey;
        try {
            String rawResponse = restTemplate.getForObject(url, String.class);
            ApiResponse apiResponse = objectMapper.readValue(rawResponse, ApiResponse.class);
            List<FlightData> allFlights = apiResponse.getFlights();

            if (aircraftModel != null && !aircraftModel.isEmpty()) {
                List<FlightData> filteredFlights = allFlights.stream()
                        .filter(flight -> aircraftModel.stream()
                                .anyMatch(model -> flight.getAircraftIcao().contains(model)))
                        .collect(Collectors.toList());
                if (!filteredFlights.isEmpty()) {
                    return filteredFlights;
                }
            }
                return allFlights;

        } catch (Exception e) {
            logger.error("Error getting flight data: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}