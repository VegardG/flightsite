package org.example.flighttrackingservice.controller;

import org.example.flighttrackingservice.model.ApiResponse;
import org.example.flighttrackingservice.model.FlightData;
import org.example.flighttrackingservice.service.FlightTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
