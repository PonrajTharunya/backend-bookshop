package org.project.mega_city_cab_service_app.service.BookingService;

public class BookingDeletionService {
    private final BookingService bookingService;

    public BookingDeletionService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public String deleteBooking(int orderNo) {
        boolean isDeleted = bookingService.deleteBooking(orderNo);
        if (isDeleted) {
            return "{\"message\": \"Booking deleted successfully!\"}";
        } else {
            return "{\"error\": \"Failed to delete booking.\"}";
        }
    }
}