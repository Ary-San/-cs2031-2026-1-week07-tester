package com.example.flyaway.dto;

import java.time.Instant;

public record BookingDTO(
        Long id,
        Instant bookingDate,
        Long flightId,
        String flightNumber,
        Long customerId,
        String customerFirstName,
        String customerLastName,
        Instant estDepartureTime,
        Instant estArrivalTime
) {
}
