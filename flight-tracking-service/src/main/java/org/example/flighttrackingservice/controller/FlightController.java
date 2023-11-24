package org.example.flighttrackingservice.controller;

import org.example.flighttrackingservice.model.FlightData;
import org.example.flighttrackingservice.service.FlightTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightTrackerService flightTrackerService;

    public FlightController(FlightTrackerService flightTrackerService) {
        this.flightTrackerService = flightTrackerService;
    }
@GetMapping("/{flightNumber}")
    public ResponseEntity<?> getFlightInfo(@PathVariable String flightNumber) {
        try {
            FlightData flightData = flightTrackerService.getFlightData(flightNumber);
            if (flightData != null) {
                return ResponseEntity.ok(flightData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Can't fetch flight data: " + e.getMessage());
        }
    }
}
