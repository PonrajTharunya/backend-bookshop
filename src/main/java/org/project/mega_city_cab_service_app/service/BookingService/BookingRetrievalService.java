package org.project.mega_city_cab_service_app.service.BookingService;
import org.project.mega_city_cab_service_app.model.booking.Booking;
import org.project.mega_city_cab_service_app.util.JsonUtils;

import java.util.List;

import static org.project.mega_city_cab_service_app.util.JsonUtils.bookingToJsonn;



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
    public String getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            return "{\"error\": \"No bookings found.\"}";
        }

        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            jsonBuilder.append(bookingToJsonn(booking));
            if (i < bookings.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

}