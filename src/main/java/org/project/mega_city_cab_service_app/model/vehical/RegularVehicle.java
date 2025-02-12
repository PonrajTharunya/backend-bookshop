package org.project.mega_city_cab_service_app.model.vehical;

import org.project.mega_city_cab_service_app.model.Vehicle;

public class RegularVehicle extends Vehicle {

    public RegularVehicle(String name, String model, String color, int year, int registrationNumber,
                          int seatingCapacity) {
        super(name, model, color, year, registrationNumber, seatingCapacity);
    }

    @Override
    public String getType() {
        return "Regular";
    }
}