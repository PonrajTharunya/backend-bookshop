package org.project.mega_city_cab_service_app.service.VehicleService;

import org.project.mega_city_cab_service_app.model.Parent.Vehicle;

public class VehicleRetrievalService {
    private final VehicleService vehicleService;

    public VehicleRetrievalService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public String getVehicle(int vehicleId) {
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        if (vehicle != null) {
            return "{\"name\": \"" + vehicle.getName() + "\", \"model\": \"" + vehicle.getModel() +
                    "\", \"color\": \"" + vehicle.getColor() + "\", \"year\": " + vehicle.getYear() +
                    ", \"registration_number\": " + vehicle.getRegistrationNumber() +
                    ", \"seating_capacity\": " + vehicle.getSeatingCapacity() +
                    ", \"type\": \"" + vehicle.getType() + "\"}";
        } else {
            return "{\"error\": \"Vehicle not found.\"}";
        }
    }
}