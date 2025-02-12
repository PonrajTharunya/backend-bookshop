package org.project.mega_city_cab_service_app.model.vehical;

import org.project.mega_city_cab_service_app.model.Parent.Vehicle;

public class PremiumVehicle extends Vehicle {
    private final boolean hasWiFi; // Unique attribute

    public PremiumVehicle(String name, String model, String color, int year, int registrationNumber,
                          int seatingCapacity, boolean hasWiFi) {
        super(name, model, color, year, registrationNumber, seatingCapacity);
        this.hasWiFi = hasWiFi;
    }

    @Override
    public String getType() {
        return "Premium";
    }

    public boolean hasWiFi() {
        return hasWiFi;
    }
}