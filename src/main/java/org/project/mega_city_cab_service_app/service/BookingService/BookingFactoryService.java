package org.project.mega_city_cab_service_app.service.BookingService;

import org.project.mega_city_cab_service_app.factory.Interface.GenericFactory;
import org.project.mega_city_cab_service_app.factory.ManageBookingFactory.WithDriverBookingFactory;
import org.project.mega_city_cab_service_app.factory.ManageBookingFactory.WithoutDriverBookingFactory;
import org.project.mega_city_cab_service_app.factory.Registry.FactoryRegistry;
import org.project.mega_city_cab_service_app.model.booking.Booking;

public class BookingFactoryService {
    private final FactoryRegistry<Booking> registry;

    public BookingFactoryService() {
        this.registry = new FactoryRegistry<>();
        registry.registerFactory("with_driver", new WithDriverBookingFactory());
        registry.registerFactory("without_driver", new WithoutDriverBookingFactory());
    }

    public GenericFactory<Booking> getBookingFactory(String bookingType) {
        GenericFactory<Booking> factory = registry.getFactory(bookingType);
        if (factory == null) {
            throw new IllegalArgumentException("Invalid booking type: " + bookingType);
        }
        return factory;
    }
}