package com.example.flyaway.controller;

import com.example.flyaway.dto.BookingDTO;
import com.example.flyaway.dto.CreateManyFlightsDTO;
import com.example.flyaway.dto.CreateManyFlightsResponseDTO;
import com.example.flyaway.dto.FlightBookRequestDTO;
import com.example.flyaway.dto.FlightDTO;
import com.example.flyaway.dto.FlightSearchResponseDTO;
import com.example.flyaway.dto.NewIdDTO;
import com.example.flyaway.service.BookingService;
import com.example.flyaway.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/flights")
public class FlightsController {
    private final FlightService flightService;
    private final BookingService bookingService;

    public FlightsController(FlightService flightService, BookingService bookingService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<NewIdDTO> createFlight(@Valid @RequestBody FlightDTO flightDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new NewIdDTO(flightService.createFlight(flightDTO).getId()));
    }

    @PostMapping("/create-many")
    public ResponseEntity<CreateManyFlightsResponseDTO> createMany(@Valid @RequestBody CreateManyFlightsDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createMany(request.getInputs()));
    }

    @GetMapping("/search")
    public ResponseEntity<FlightSearchResponseDTO> search(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) Instant estDepartureTimeFrom,
            @RequestParam(required = false) Instant estDepartureTimeTo) {
        return ResponseEntity.ok(new FlightSearchResponseDTO(
                flightService.search(flightNumber, airlineName, estDepartureTimeFrom, estDepartureTimeTo)
        ));
    }

    @PostMapping("/book")
    public ResponseEntity<NewIdDTO> book(@Valid @RequestBody FlightBookRequestDTO request,
                                         Authentication authentication) {
        return ResponseEntity.ok(new NewIdDTO(bookingService.book(request.getFlightId(), authentication).id()));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }
}
