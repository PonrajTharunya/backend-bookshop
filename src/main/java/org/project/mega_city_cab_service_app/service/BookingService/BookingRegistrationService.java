package org.project.mega_city_cab_service_app.service.BookingService;

import org.project.mega_city_cab_service_app.factory.Interface.GenericFactory;
import org.project.mega_city_cab_service_app.model.booking.Booking;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class BookingRegistrationService {
    private final BookingService bookingService;
    private final BookingFactoryService bookingFactoryService;

    public BookingRegistrationService(BookingService bookingService, BookingFactoryService bookingFactoryService) {
        this.bookingService = bookingService;
        this.bookingFactoryService = bookingFactoryService;
    }

    public String registerBooking(String jsonInput) {
        String bookingType = JsonUtils.extractValueFromJson(jsonInput, "booking_type");

        // Get the appropriate factory
        GenericFactory<Booking> factory = bookingFactoryService.getBookingFactory(bookingType);

        // Create the booking object
        Booking booking = factory.create(jsonInput);

        // Register the booking
        boolean isRegistered = bookingService.registerBooking(booking);
        if (isRegistered) {
            return "{\"message\": \"Booking registered successfully!\"}";
        } else {
            return "{\"error\": \"Failed to register booking.\"}";
        }
    }
}