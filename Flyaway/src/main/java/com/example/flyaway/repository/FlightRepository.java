package com.example.hola.repository;

import com.example.hola.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByNumeroVuelo(String numeroVuelo);
}
