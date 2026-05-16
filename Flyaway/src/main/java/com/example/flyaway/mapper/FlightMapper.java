package com.example.hola.mapper;

import com.example.hola.dto.FlightDTO;
import com.example.hola.model.Flight;

public class FlightMapper {

    private FlightMapper() {
    }

    public static Flight toEntity(FlightDTO dto) {
        return new Flight(
                dto.getId(),
                dto.getNumeroVuelo(),
                dto.getHoraSalida(),
                dto.getHoraLlegada(),
                dto.getAsientosDisponibles()
        );
    }

    public static FlightDTO toDto(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getNumeroVuelo(),
                flight.getHoraSalida(),
                flight.getHoraLlegada(),
                flight.getAsientosDisponibles()
        );
    }
}
