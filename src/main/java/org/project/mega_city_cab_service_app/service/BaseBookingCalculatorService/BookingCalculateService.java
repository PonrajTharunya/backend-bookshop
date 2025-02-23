package org.project.mega_city_cab_service_app.service.BaseBookingCalculatorService;

import org.project.mega_city_cab_service_app.dao.BookingDAO;
import org.project.mega_city_cab_service_app.factory.BookingCalculatorFactory.BookingCalculatorFactory;
import org.project.mega_city_cab_service_app.model.PlanPrice.PlanPrice;
import org.project.mega_city_cab_service_app.model.Range.DistanceRange;
import org.project.mega_city_cab_service_app.model.booking.Booking;
import org.project.mega_city_cab_service_app.model.calculaterent.FinalAmountResponse;
import org.project.mega_city_cab_service_app.model.tax.Tax;
import org.project.mega_city_cab_service_app.util.JsonUtils;

import java.time.LocalDateTime;
import java.util.Map;

public class BookingCalculateService {
    private final BookingCalculatorFactory calculatorFactory;

    public BookingCalculateService() {
        this.calculatorFactory = new BookingCalculatorFactory();
    }

    public String calculateFinalAmount(int orderId, int actualKmUsed, LocalDateTime returnDate, Map<String, Object> inputData) {
        // Retrieve the booking by order ID
        BookingDAO bookingDAO = new BookingDAO();
        Booking booking = bookingDAO.findBookingByID(orderId);

        if (booking == null) {
            System.out.println("Booking not found for order ID: " + orderId);
            return "{\"error\": \"Booking not found.\"}";
        }

        // Get the appropriate calculator
        BookingCalculator calculator = calculatorFactory.getCalculator(booking.getBookingType());

        // Calculate the final amount
        double finalAmount = calculator.calculateAmount(booking, actualKmUsed, returnDate);

        // Extract driver fee from input data
        double driverFee = 0;
        if (inputData != null && inputData.containsKey("driverFee")) {
            try {
                driverFee = Double.parseDouble(inputData.get("driverFee").toString());
            } catch (NumberFormatException e) {
                System.out.println("Invalid driverFee value provided. Defaulting to 0.");
            }
        }

        // Check if the booking has a driver
        boolean hasDriver = booking.getDriverId() != null && booking.getDriver() != null;
        if (!hasDriver) {
            driverFee = 0; // No driver fee if no driver is associated with the booking
        }

        // Prepare detailed breakdown
        int initialKm = booking.getInitialKm();
        int kmUsed = actualKmUsed - initialKm;
        PlanPrice planPrice = booking.getPlanPrice();
        DistanceRange distanceRange = booking.getDistanceRange();
        Tax tax = booking.getTax();

        String distanceUsed = kmUsed + " km";
        String basePrice = planPrice.getPrice() + "";
        String extraKilometers = kmUsed > distanceRange.getMaxDistance()
                ? (kmUsed - distanceRange.getMaxDistance()) + " km at " + planPrice.getExtraKmPrice() + " per km → "
                + (kmUsed - distanceRange.getMaxDistance()) + " * " + planPrice.getExtraKmPrice() + " = "
                + ((kmUsed - distanceRange.getMaxDistance()) * planPrice.getExtraKmPrice())
                : "0 km";
        String totalCostBeforeDiscount = (planPrice.getPrice() + (kmUsed > distanceRange.getMaxDistance()
                ? (kmUsed - distanceRange.getMaxDistance()) * planPrice.getExtraKmPrice()
                : 0)) + "";
        String discount = planPrice.getDiscount() > 0
                ? planPrice.getDiscount() + "% of " + totalCostBeforeDiscount + " = "
                + (Double.parseDouble(totalCostBeforeDiscount) * (planPrice.getDiscount() / 100))
                : "0%";
        String totalCostAfterDiscount = (Double.parseDouble(totalCostBeforeDiscount)
                - (planPrice.getDiscount() > 0
                ? Double.parseDouble(totalCostBeforeDiscount) * (planPrice.getDiscount() / 100)
                : 0)) + "";
        String taxAmount = tax != null && tax.isStatus() && tax.getTaxRate() > 0
                ? tax.getTaxRate() + "% of " + totalCostAfterDiscount + " = "
                + (Double.parseDouble(totalCostAfterDiscount) * (tax.getTaxRate() / 100))
                : "0%";
        String calculationBreakdown = "Distance Used: " + distanceUsed + "\n"
                + "Base Price: " + basePrice + "\n"
                + "Extra Kilometers: " + extraKilometers + "\n"
                + "Total Cost Before Discount: " + totalCostBeforeDiscount + "\n"
                + "Discount: " + discount + "\n"
                + "Total Cost After Discount: " + totalCostAfterDiscount + "\n"
                + "Tax: " + taxAmount + "\n";

        // Add driver fee to the breakdown only if applicable
        if (hasDriver && driverFee > 0) {
            calculationBreakdown += "Driver Fee: " + driverFee + "\n";
            finalAmount += driverFee; // Add driver fee to the final amount
        }

        calculationBreakdown += "Final Total Cost: " + finalAmount;

        // Return the result as JSON
        FinalAmountResponse response = new FinalAmountResponse(
                orderId,
                finalAmount,
                distanceUsed,
                basePrice,
                extraKilometers,
                totalCostBeforeDiscount,
                discount,
                totalCostAfterDiscount,
                taxAmount,
                calculationBreakdown,
                hasDriver && driverFee > 0 ? driverFee + "" : "0" // Include driver fee only if applicable
        );

        return JsonUtils.toJson(response);
    }
}