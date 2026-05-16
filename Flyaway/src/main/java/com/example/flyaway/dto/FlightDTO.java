package com.example.flyaway.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public class FlightDTO {
    private Long id;

    @NotBlank
    private String airlineName;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{3}$")
    private String flightNumber;

    @NotNull
    private Instant estDepartureTime;

    @NotNull
    private Instant estArrivalTime;

    @NotNull
    @Positive
    private Integer availableSeats;

    public FlightDTO() {
    }

    public FlightDTO(Long id, String airlineName, String flightNumber, Instant estDepartureTime,
                     Instant estArrivalTime, Integer availableSeats) {
        this.id = id;
        this.airlineName = airlineName;
        this.flightNumber = flightNumber;
        this.estDepartureTime = estDepartureTime;
        this.estArrivalTime = estArrivalTime;
        this.availableSeats = availableSeats;
    }

    @AssertTrue
    public boolean isScheduleValid() {
        if (estDepartureTime == null || estArrivalTime == null) {
            return true;
        }
        return estDepartureTime.isBefore(estArrivalTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Instant getEstDepartureTime() {
        return estDepartureTime;
    }

    public void setEstDepartureTime(Instant estDepartureTime) {
        this.estDepartureTime = estDepartureTime;
    }

    public Instant getEstArrivalTime() {
        return estArrivalTime;
    }

    public void setEstArrivalTime(Instant estArrivalTime) {
        this.estArrivalTime = estArrivalTime;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
}
