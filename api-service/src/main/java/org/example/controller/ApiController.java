package org.example.flighttrackingservice.controller;

import org.example.flighttrackingservice.model.ApiResponse;
import org.example.flighttrackingservice.model.FlightData;
import org.example.flighttrackingservice.service.FlightTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://http://localhost:63342")
@RestController
@RequestMapping("/flights")
public class ApiController {
    private final FlightTrackerService flightTrackerService;

    public ApiController(FlightTrackerService flightTrackerService) {
        this.flightTrackerService = flightTrackerService;
    }
    @GetMapping("/{flightNumber}")
    public ResponseEntity<?> getFlightInfo(@PathVariable String flightNumber) {
        try {
            List<FlightData> flightDataList = flightTrackerService.getFlightData(flightNumber);
            if (!flightDataList.isEmpty()) {
                return ResponseEntity.ok(flightDataList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Can't fetch flight data: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFlightInfo() {
        try {
            List<FlightData> allFlightData = flightTrackerService.getAllFlights();
            if (!allFlightData.isEmpty()) {
                return ResponseEntity.ok(allFlightData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unable to get all flight data: " + e.getMessage());
        }
    }

    /*@GetMapping("/bounds")
    public ResponseEntity<?> getFlightsInRange(
            @RequestParam double minLat,
            @RequestParam double maxLat,
            @RequestParam double minLng,
            @RequestParam double maxLng) {
        List<FlightData> flights = flightTrackerService.getFlightsInRange(minLat, maxLat, minLng, maxLng);
        if (!flights.isEmpty()) {
            return ResponseEntity.ok(flights);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}