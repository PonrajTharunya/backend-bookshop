package org.project.mega_city_cab_service_app.service.VehicleService;

import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.model.vehical.PremiumVehicle;
import org.project.mega_city_cab_service_app.model.vehical.VIPVehicle;

public class VehicleRetrievalService {
    private final VehicleService vehicleService;

    public VehicleRetrievalService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Retrieves a vehicle by its ID and returns a JSON representation.
     */
    public String getVehicle(int vehicleId) {
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        if (vehicle != null) {
            return buildVehicleJson(vehicle);
        } else {
            return "{\"error\": \"Vehicle not found.\"}";
        }
    }

    /**
     * Builds a JSON string based on the type of the vehicle.
     */
    private String buildVehicleJson(Vehicle vehicle) {
        StringBuilder jsonBuilder = new StringBuilder();

        // Common fields for all vehicles
        jsonBuilder.append("{\"id\": ").append(vehicle.getId())
                .append(", \"name\": \"").append(vehicle.getName()).append("\"")
                .append(", \"model\": \"").append(vehicle.getModel()).append("\"")
                .append(", \"color\": \"").append(vehicle.getColor()).append("\"")
                .append(", \"year\": ").append(vehicle.getYear())
                .append(", \"registration_number\": ").append(vehicle.getRegistrationNumber())
                .append(", \"seating_capacity\": ").append(vehicle.getSeatingCapacity())
                .append(", \"type\": \"").append(vehicle.getType()).append("\"");

        // Add type-specific fields
        switch (vehicle.getType()) {
            case "Premium":
                PremiumVehicle premiumVehicle = (PremiumVehicle) vehicle;
                jsonBuilder.append(", \"has_wifi\": ").append(premiumVehicle.hasWiFi());
                break;

            case "VIP":
                VIPVehicle vipVehicle = (VIPVehicle) vehicle;
                jsonBuilder.append(", \"has_chauffeur_service\": ").append(vipVehicle.hasChauffeurService());
                break;

            case "Regular":
                // No additional fields for Regular vehicles, but you can add them if needed
                break;

            default:
                throw new IllegalArgumentException("Unsupported vehicle type: " + vehicle.getType());
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}