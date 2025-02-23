package org.project.mega_city_cab_service_app.service.BaseBookingCalculatorService;

import org.project.mega_city_cab_service_app.model.booking.Booking;

import java.time.LocalDateTime;

public interface BookingCalculator {
    double calculateAmount(Booking booking, int actualKmUsed, LocalDateTime returnDate);
}


