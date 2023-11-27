package org.example.flighttrackingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightData {

    @JsonProperty("hex")
    private String hex;

    @JsonProperty("reg_number")
    private String regNumber;

    @JsonProperty("flag")
    private String flag;

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("lng")
    private Double longitude;

    @JsonProperty("alt")
    private Integer altitude;

    @JsonProperty("dir")
    private Integer direction;

    @JsonProperty("speed")
    private Integer speed;

    @JsonProperty("v_speed")
    private Integer verticalSpeed;

    @JsonProperty("squawk")
    private String squawk;

    @JsonProperty("flight_number")
    private String flightNumber;

    @JsonProperty("flight_icao")
    private String flightIcao;

    @JsonProperty("flight_iata")
    private String flightIata;

    @JsonProperty("dep_icao")
    private String departureIcao;

    @JsonProperty("dep_iata")
    private String departureIata;

    @JsonProperty("arr_icao")
    private String arrivalIcao;

    @JsonProperty("arr_iata")
    private String arrivalIata;

    @JsonProperty("airline_icao")
    private String airlineIcao;

    @JsonProperty("airline_iata")
    private String airlineIata;

    @JsonProperty("aircraft_icao")
    private String aircraftIcao;

    @JsonProperty("updated")
    private Long updated;

    @JsonProperty("status")
    private String status;

    @JsonProperty("type")
    private String type;

    // Getters and Setters for all fields
}