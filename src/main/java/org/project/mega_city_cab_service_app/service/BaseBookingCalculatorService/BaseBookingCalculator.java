package org.project.mega_city_cab_service_app.service.BaseBookingCalculatorService;

import org.project.mega_city_cab_service_app.model.PlanPrice.PlanPrice;
import org.project.mega_city_cab_service_app.model.Range.DistanceRange;
import org.project.mega_city_cab_service_app.model.booking.Booking;
import org.project.mega_city_cab_service_app.model.tax.Tax;

import java.time.LocalDateTime;

public class BaseBookingCalculator implements BookingCalculator {

    @Override
    public double calculateAmount(Booking booking, int actualKmUsed, LocalDateTime returnDate) {
        // Validate booking data
        if (booking == null || booking.getPlanPrice() == null || booking.getDistanceRange() == null) {
            throw new IllegalArgumentException("Booking data is incomplete.");
        }

        // Calculate distance used
        int initialKm = booking.getInitialKm();
        int kmUsed = actualKmUsed - initialKm;

        // Get plan price and distance range
        PlanPrice planPrice = booking.getPlanPrice();
        DistanceRange distanceRange = booking.getDistanceRange();

        double basePrice = planPrice.getPrice();
        double extraKmPrice = planPrice.getExtraKmPrice();
        double discount = planPrice.getDiscount();
        int maxDistance = distanceRange.getMaxDistance();

        // Calculate cost for distance
        double totalCost = 0;
        if (kmUsed <= maxDistance) {
            totalCost = basePrice; // Within range
        } else {
            int extraKm = kmUsed - maxDistance;
            totalCost = basePrice + (extraKm * extraKmPrice); // Base price + extra km cost
        }

        // Apply discount
        double discountedCost = totalCost;
        if (discount > 0) {
            discountedCost -= (totalCost * (discount / 100)); // Deduct discount percentage
        }

        // Add tax
        Tax tax = booking.getTax();
        double taxAmount = 0;
        if (tax != null && tax.isStatus() && tax.getTaxRate() > 0) {
            taxAmount = discountedCost * (tax.getTaxRate() / 100);
            discountedCost += taxAmount;
        }

        return Math.round(discountedCost * 100.0) / 100.0; // Round to 2 decimal places
    }
}