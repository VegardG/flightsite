package org.example.flighttrackingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    @JsonProperty("response")
    private List<FlightData> flights;

    public List<FlightData> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightData> flights) {
        this.flights = flights;
    }
}

