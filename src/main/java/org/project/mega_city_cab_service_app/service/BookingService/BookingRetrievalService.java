package org.project.mega_city_cab_service_app.service.BookingService;
import org.project.mega_city_cab_service_app.model.booking.Booking;
import org.project.mega_city_cab_service_app.util.JsonUtils;


public class BookingRetrievalService {
    private final BookingService bookingService;

    public BookingRetrievalService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public String getBooking(int orderNo) {
        Booking booking = bookingService.getBooking(orderNo);
        if (booking != null) {
            return JsonUtils.toJson(booking);
        } else {
            return "{\"error\": \"Booking not found.\"}";
        }
    }
}