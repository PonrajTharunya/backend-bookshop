package org.project.mega_city_cab_service_app.factory.ManageBookingFactory;

import org.project.mega_city_cab_service_app.factory.Interface.GenericFactory;
import org.project.mega_city_cab_service_app.model.booking.Booking;
import org.project.mega_city_cab_service_app.util.JsonUtils;

import java.time.LocalDateTime;

public class WithoutDriverBookingFactory implements GenericFactory<Booking> {

    @Override
    public Booking create(String jsonInput) {
        int customerId = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "customer_id"));
        String pickupLocation = JsonUtils.extractValueFromJson(jsonInput, "pickup_location");
        String dropLocation = JsonUtils.extractValueFromJson(jsonInput, "drop_location");
        String bookingType = JsonUtils.extractValueFromJson(jsonInput, "booking_type");
        int vehicleId = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "vehicle_id"));
        int initialKm = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "initial_km"));
        int finalKm = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "final_km"));
        LocalDateTime pickupTime = LocalDateTime.parse(JsonUtils.extractValueFromJson(jsonInput, "pickup_time"));
        LocalDateTime returnTime = LocalDateTime.parse(JsonUtils.extractValueFromJson(jsonInput, "return_time"));
        int daysNeeded = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "days_needed"));
        int tax_id=Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput,"tax_id"));
        int employee_id=Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput,"employee_id"));
        int package_id=Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput,"package_id"));

        // Driver ID is not required for "without_driver" bookings
        return new Booking(customerId, pickupLocation, dropLocation, bookingType, vehicleId, null, initialKm, finalKm, pickupTime, returnTime, daysNeeded,tax_id,employee_id,package_id);
    }
}