package org.example.flighttrackingservice.controller;

import org.example.flighttrackingservice.model.FlightData;
import org.example.flighttrackingservice.service.FlightTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/flights")
public class ApiController {
    private final FlightTrackerService flightTrackerService;

    // Constructer to initialize the FlightTrackerService
    public ApiController(FlightTrackerService flightTrackerService) {
        this.flightTrackerService = flightTrackerService;
    }

    // Endpoint to get information about a flight using its flight number
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

    // Endpoint to get information about all flights, can be filtered by the model
    @GetMapping("/all")
    public ResponseEntity<?> getAllFlightInfo(@RequestParam(required = false) List<String> models) {
        try {
            List<FlightData> allFlightData = flightTrackerService.getAllFlights(models);
            if (!allFlightData.isEmpty()) {
                return ResponseEntity.ok(allFlightData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unable to get all flight data: " + e.getMessage());
        }
    }
}
