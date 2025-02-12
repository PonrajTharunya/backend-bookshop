package org.project.mega_city_cab_service_app.model.vehical;

import org.project.mega_city_cab_service_app.model.Parent.Vehicle;

public class VIPVehicle extends Vehicle {
    private final boolean hasChauffeurService; // Unique attribute

    public VIPVehicle(String name, String model, String color, int year, int registrationNumber,
                      int seatingCapacity, boolean hasChauffeurService) {
        super(name, model, color, year, registrationNumber, seatingCapacity);
        this.hasChauffeurService = hasChauffeurService;
    }

    @Override
    public String getType() {
        return "VIP";
    }

    public boolean hasChauffeurService() {
        return hasChauffeurService;
    }
}
