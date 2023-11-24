package org.example.flighttrackingservice.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightData {

    @JsonProperty("flight_iata")
    private String flightIata;

    @JsonProperty("squawk")
    private String squawk;

    @JsonProperty("airline_icao")
    private String airlineIcao;

    @JsonProperty("flight_number")
    private String flightNumber;

    @JsonProperty("dep_icao")
    private String depIcao;

    @JsonProperty("flag")
    private String flag;
}


