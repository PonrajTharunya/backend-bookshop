package org.project.mega_city_cab_service_app.service.BookingService;

import org.project.mega_city_cab_service_app.dao.BookingDAO;
import org.project.mega_city_cab_service_app.model.booking.Booking;

public class BookingService {
    private final BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    // Register a new booking
    public boolean registerBooking(Booking booking) {
        // Add any additional business logic here (e.g., validation)
        return bookingDAO.addBooking(booking);
    }

    // Retrieve a booking by its order number
    public Booking getBooking(int orderNo) {
        return bookingDAO.findBookingByID(orderNo);
    }

    // Update an existing booking
    public boolean updateBooking(Booking booking) {
        // Add any additional business logic here (e.g., validation)
        return bookingDAO.updateBooking(booking);
    }

    // Delete a booking by its order number
    public boolean deleteBooking(int orderNo) {
        return bookingDAO.deleteBooking(orderNo);
    }
}