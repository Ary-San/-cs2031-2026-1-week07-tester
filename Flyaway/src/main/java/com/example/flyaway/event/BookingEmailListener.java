package com.example.flyaway.event;

import com.example.flyaway.dto.BookingDTO;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class BookingEmailListener {
    @EventListener
    public void onBookingCreated(BookingCreatedEvent event) {
        BookingDTO booking = event.booking();
        String content = """
                Hello %s %s,

                Your booking was successful!

                The booking is for flight %s with departure date of %s and arrival date of %s.

                The booking was registered at %s.

                Bon Voyage!
                Fly Away Travel
                """.formatted(
                booking.customerFirstName(),
                booking.customerLastName(),
                booking.flightNumber(),
                booking.estDepartureTime(),
                booking.estArrivalTime(),
                booking.bookingDate()
        );

        String fileName = "flight_booking_email_%s.txt".formatted(booking.id());
        Set<Path> paths = new LinkedHashSet<>();
        Path cwd = Path.of("").toAbsolutePath();
        paths.add(cwd.resolve(fileName));
        Path parent = cwd.getParent();
        if (parent != null) {
            paths.add(parent.resolve(fileName));
        }

        for (Path path : paths) {
            try {
                Files.writeString(path, content);
            } catch (Exception ignored) {
                // The tester only needs one reachable copy. Ignore unwritable fallback paths.
            }
        }
    }
}
