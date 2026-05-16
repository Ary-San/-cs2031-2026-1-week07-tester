package com.example.flyaway.mapper;

import com.example.flyaway.dto.FlightDTO;
import com.example.flyaway.model.Flight;

public class FlightMapper {
    private FlightMapper() {
    }

    public static Flight toEntity(FlightDTO dto) {
        var flight = new Flight();
        flight.setId(dto.getId());
        flight.setAirlineName(dto.getAirlineName());
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setEstDepartureTime(dto.getEstDepartureTime());
        flight.setEstArrivalTime(dto.getEstArrivalTime());
        flight.setAvailableSeats(dto.getAvailableSeats());
        return flight;
    }

    public static FlightDTO toDto(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getAirlineName(),
                flight.getFlightNumber(),
                flight.getEstDepartureTime(),
                flight.getEstArrivalTime(),
                flight.getAvailableSeats()
        );
    }
}
