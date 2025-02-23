package org.project.mega_city_cab_service_app.factory.BookingCalculatorFactory;

import org.project.mega_city_cab_service_app.service.BaseBookingCalculatorService.BaseBookingCalculator;
import org.project.mega_city_cab_service_app.service.BaseBookingCalculatorService.BookingCalculator;

public class BookingCalculatorFactory {
    public static BookingCalculator getCalculator(String bookingType) {
        switch (bookingType.toLowerCase()) {
            case "with_driver":
                return new BaseBookingCalculator(); // Default calculator for now
            case "without_driver":
                return new BaseBookingCalculator(); // Extend this for specific logic if needed
            default:
                throw new IllegalArgumentException("Unsupported booking type: " + bookingType);
        }
    }
}