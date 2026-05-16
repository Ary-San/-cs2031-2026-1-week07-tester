package com.example.flyaway.dto;

import jakarta.validation.constraints.NotNull;

public class FlightBookRequestDTO {
    @NotNull
    private Long flightId;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
