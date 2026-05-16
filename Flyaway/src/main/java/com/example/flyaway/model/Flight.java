package com.example.flyaway.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 6)
    private String flightNumber;

    @Column(nullable = false)
    private String airlineName;

    @Column(nullable = false)
    private Instant estDepartureTime;

    @Column(nullable = false)
    private Instant estArrivalTime;

    @Column(nullable = false)
    private Integer availableSeats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
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
