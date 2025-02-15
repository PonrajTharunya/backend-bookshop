package org.project.mega_city_cab_service_app.service.BookingService;

import org.project.mega_city_cab_service_app.model.booking.Booking;

public class BookingRetrievalService {
    private final BookingService bookingService;

    public BookingRetrievalService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public String getBooking(int orderNo) {
        Booking booking = bookingService.getBooking(orderNo);
        if (booking != null) {
            return "{\"order_no\": " + booking.getOrderNo() +
                    ", \"customer_id\": " + booking.getCustomerId() +
                    ", \"pickup_location\": \"" + booking.getPickupLocation() + "\"" +
                    ", \"drop_location\": \"" + booking.getDropLocation() + "\"" +
                    ", \"booking_type\": \"" + booking.getBookingType() + "\"" +
                    ", \"vehicle_id\": " + booking.getVehicleId() +
                    ", \"driver_id\": " + booking.getDriverId() +
                    ", \"initial_km\": " + booking.getInitialKm() +
                    ", \"final_km\": " + booking.getFinalKm() +
                    ", \"pickup_time\": \"" + booking.getPickupTime() + "\"" +
                    ", \"return_time\": \"" + booking.getReturnTime() + "\"" +
                    ", \"days_needed\": " + booking.getDaysNeeded() + "}";
        } else {
            return "{\"error\": \"Booking not found.\"}";
        }
    }
}