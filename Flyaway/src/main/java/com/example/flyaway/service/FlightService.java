package com.example.flyaway.service;

import com.example.flyaway.dto.CreateManyFlightsResponseDTO;
import com.example.flyaway.dto.FlightDTO;
import com.example.flyaway.mapper.FlightMapper;
import com.example.flyaway.model.Flight;
import com.example.flyaway.repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightDTO createFlight(FlightDTO flightDTO) {
        if (flightRepository.existsByFlightNumber(flightDTO.getFlightNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight number already exists");
        }
        Flight savedFlight = flightRepository.save(FlightMapper.toEntity(flightDTO));
        return FlightMapper.toDto(savedFlight);
    }

    public CreateManyFlightsResponseDTO createMany(List<FlightDTO> inputs) {
        var ids = new ArrayList<Long>();
        for (FlightDTO input : inputs) {
            ids.add(createFlight(input).getId());
        }
        return new CreateManyFlightsResponseDTO(ids);
    }

    public List<FlightDTO> search(String flightNumber, String airlineName, Instant from, Instant to) {
        List<Flight> flights = flightRepository.findAllByOrderByIdAsc();
        return flights.stream()
                .filter(f -> flightNumber == null || f.getFlightNumber().contains(flightNumber))
                .filter(f -> airlineName == null || f.getAirlineName().contains(airlineName))
                .filter(f -> from == null || !f.getEstDepartureTime().isBefore(from))
                .filter(f -> to == null || !f.getEstDepartureTime().isAfter(to))
                .map(FlightMapper::toDto)
                .toList();
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
    }

    public void deleteAll() {
        flightRepository.deleteAll();
    }
}
