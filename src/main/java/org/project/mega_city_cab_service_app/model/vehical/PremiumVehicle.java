package org.project.mega_city_cab_service_app.model.vehical;

import org.project.mega_city_cab_service_app.model.Vehical;

public class PremiumVehicle extends Vehical {
    private final int seetingCapacity;
    private final double fareRate;
    private final boolean haswifi;


    public PremiumVehicle(String name, String model, String color, int year, int registrationNumber,
                          int seatingCapacity, double fareRate, boolean hasWiFi) {
        super(name, model, color, "Premium", year, registrationNumber);
        this.seatingCapacity = seatingCapacity;
        this.fareRate = fareRate;
        this.hasWiFi = hasWiFi;
    }



}
