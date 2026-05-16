package com.example.flyaway.controller;

import com.example.flyaway.service.BookingService;
import com.example.flyaway.service.FlightService;
import com.example.flyaway.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cleanup")
public class CleanupController {
    private final BookingService bookingService;
    private final FlightService flightService;
    private final UserService userService;

    public CleanupController(BookingService bookingService, FlightService flightService, UserService userService) {
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.userService = userService;
    }

    @DeleteMapping
    public ResponseEntity<Void> cleanup() {
        bookingService.deleteAll();
        flightService.deleteAll();
        userService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
