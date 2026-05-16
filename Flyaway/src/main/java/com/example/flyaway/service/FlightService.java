package com.example.hola.service;

import com.example.hola.dto.FlightDTO;
import com.example.hola.mapper.FlightMapper;
import com.example.hola.model.Flight;
import com.example.hola.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightDTO createFlight(FlightDTO flightDTO) {
        if (flightRepository.existsByNumeroVuelo(flightDTO.getNumeroVuelo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de vuelo ya existe");
        }

        Flight flight = FlightMapper.toEntity(flightDTO);
        Flight savedFlight = flightRepository.save(flight);

        return FlightMapper.toDto(savedFlight);
    }
}
