package com.example.flyaway.repository;

import com.example.flyaway.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByFlightNumberContainingIgnoreCaseOrderByIdAsc(String flightNumber);

    List<Flight> findByAirlineNameContainingIgnoreCaseOrderByIdAsc(String airlineName);

    List<Flight> findByEstDepartureTimeGreaterThanEqualOrderByIdAsc(Instant from);

    List<Flight> findByEstDepartureTimeLessThanEqualOrderByIdAsc(Instant to);

    List<Flight> findAllByOrderByIdAsc();
}
