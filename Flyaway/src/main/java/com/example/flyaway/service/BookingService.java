package com.example.flyaway.service;

import com.example.flyaway.dto.BookingDTO;
import com.example.flyaway.event.BookingCreatedEvent;
import com.example.flyaway.model.Booking;
import com.example.flyaway.model.Flight;
import com.example.flyaway.model.User;
import com.example.flyaway.repository.BookingRepository;
import com.example.flyaway.repository.FlightRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public BookingDTO book(Long flightId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));

        if (flight.getAvailableSeats() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight cannot be oversold");
        }

        Instant now = Instant.now().truncatedTo(ChronoUnit.MICROS);
        if (!flight.getEstDepartureTime().isAfter(now) || !flight.getEstArrivalTime().isAfter(now)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flight is in the past or in transit");
        }

        boolean overlaps = bookingRepository.findByCustomerId(user.getId()).stream()
                .map(Booking::getFlight)
                .anyMatch(existing -> flight.getEstDepartureTime().isBefore(existing.getEstArrivalTime())
                        && flight.getEstArrivalTime().isAfter(existing.getEstDepartureTime()));
        if (overlaps) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Overlapping flight");
        }

        var booking = new Booking();
        booking.setBookingDate(now);
        booking.setFlight(flight);
        booking.setCustomer(user);

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);
        Booking saved = bookingRepository.save(booking);
        BookingDTO dto = toDto(saved);
        eventPublisher.publishEvent(new BookingCreatedEvent(dto));
        return dto;
    }

    @Transactional(readOnly = true)
    public BookingDTO getBooking(Long id) {
        return bookingRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
    }

    public void deleteAll() {
        bookingRepository.deleteAll();
    }

    private BookingDTO toDto(Booking booking) {
        Flight flight = booking.getFlight();
        User customer = booking.getCustomer();
        return new BookingDTO(
                booking.getId(),
                booking.getBookingDate(),
                flight.getId(),
                flight.getFlightNumber(),
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                flight.getEstDepartureTime(),
                flight.getEstArrivalTime()
        );
    }
}
