package org.project.mega_city_cab_service_app.service.BookingService;

import org.project.mega_city_cab_service_app.factory.Interface.GenericFactory;
import org.project.mega_city_cab_service_app.model.booking.Booking;
import org.project.mega_city_cab_service_app.util.JsonUtils;

import java.time.LocalDateTime;

public class BookingUpdateService {
    private final BookingService bookingService;
    private final BookingFactoryService bookingFactoryService;

    public BookingUpdateService(BookingService bookingService, BookingFactoryService bookingFactoryService) {
        this.bookingService = bookingService;
        this.bookingFactoryService = bookingFactoryService;
    }

    public String updateBooking(int orderNo, String jsonInput) {
        // Extract fields from JSON input
        String bookingType = JsonUtils.extractValueFromJson(jsonInput, "booking_type");
        int customerId = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "customer_id"));
        String pickupLocation = JsonUtils.extractValueFromJson(jsonInput, "pickup_location");
        String dropLocation = JsonUtils.extractValueFromJson(jsonInput, "drop_location");
        int vehicleId = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "vehicle_id"));
        Integer driverId = "with_driver".equals(bookingType)
                ? Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "driver_id"))
                : null;
        int initialKm = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "initial_km"));
        int finalKm = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "final_km"));
        String pickupTimeStr = JsonUtils.extractValueFromJson(jsonInput, "pickup_time");
        String returnTimeStr = JsonUtils.extractValueFromJson(jsonInput, "return_time");
        int daysNeeded = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "days_needed"));

        // Validate required fields
        if (bookingType == null || pickupLocation == null || dropLocation == null || pickupTimeStr == null ||
                returnTimeStr == null) {
            return "{\"error\": \"Missing required fields in JSON input.\"}";
        }

        // Parse date-time fields
        LocalDateTime pickupTime = JsonUtils.parseLocalDateTime(pickupTimeStr);
        LocalDateTime returnTime = JsonUtils.parseLocalDateTime(returnTimeStr);

        // Get the factory from the factory service
        GenericFactory<Booking> factory = bookingFactoryService.getBookingFactory(bookingType);
        if (factory == null) {
            return "{\"error\": \"Invalid booking type.\"}";
        }

        // Create the updated booking object
        Booking updatedBooking = factory.create(jsonInput);
        updatedBooking.setOrderNo(orderNo); // Set the order number manually

        // Update the booking
        boolean isUpdated = bookingService.updateBooking(updatedBooking);
        if (isUpdated) {
            return "{\"message\": \"Booking updated successfully!\"}";
        } else {
            return "{\"error\": \"Failed to update booking.\"}";
        }
    }
}