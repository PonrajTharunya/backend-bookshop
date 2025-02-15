package org.project.mega_city_cab_service_app.service.VehicleService;

import org.project.mega_city_cab_service_app.factory.Interface.VehicleFactory;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class VehicleUpdateService {
    private final VehicleService vehicleService;
    private final VehicleFactoryService vehicleFactoryService;

    public VehicleUpdateService(VehicleService vehicleService, VehicleFactoryService vehicleFactoryService) {
        this.vehicleService = vehicleService;
        this.vehicleFactoryService = vehicleFactoryService;
    }

    public String updateVehicle(int originalVehicleId, String jsonInput) {
        // Extract fields from JSON input
        String type = JsonUtils.extractValueFromJson(jsonInput, "type");
        String name = JsonUtils.extractValueFromJson(jsonInput, "name");
        String model = JsonUtils.extractValueFromJson(jsonInput, "model");
        String color = JsonUtils.extractValueFromJson(jsonInput, "color");
        String yearStr = JsonUtils.extractValueFromJson(jsonInput, "year");
        String registrationNumberStr = JsonUtils.extractValueFromJson(jsonInput, "registration_number");
        String seatingCapacityStr = JsonUtils.extractValueFromJson(jsonInput, "seating_capacity");

        // Validate required fields
        if (type == null || name == null || model == null || color == null || yearStr == null ||
                registrationNumberStr == null || seatingCapacityStr == null) {
            return "{\"error\": \"Missing required fields in JSON input.\"}";
        }

        // Parse numeric fields
        int year;
        int registrationNumber;
        int seatingCapacity;
        try {
            year = Integer.parseInt(yearStr);
            registrationNumber = Integer.parseInt(registrationNumberStr);
            seatingCapacity = Integer.parseInt(seatingCapacityStr);
        } catch (NumberFormatException e) {
            return "{\"error\": \"Invalid numeric field in JSON input.\"}";
        }

        // Get the factory from the factory service
        VehicleFactory factory = vehicleFactoryService.getVehicleFactory(type);
        if (factory == null) {
            return "{\"error\": \"Invalid vehicle type.\"}";
        }

        // Create the updated vehicle object
        Vehicle updatedVehicle = factory.createVehicle(jsonInput, name, model, color, year, registrationNumber, seatingCapacity);

        // Update the vehicle
        boolean isUpdated = vehicleService.updateVehicle(updatedVehicle);
        if (isUpdated) {
            return "{\"message\": \"Vehicle updated successfully!\"}";
        } else {
            return "{\"error\": \"Failed to update vehicle.\"}";
        }
    }
}