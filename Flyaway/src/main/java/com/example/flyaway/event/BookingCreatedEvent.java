package com.example.flyaway.event;

import com.example.flyaway.dto.BookingDTO;

public record BookingCreatedEvent(BookingDTO booking) {
}
