package org.project.mega_city_cab_service_app.service.VehicleService;

public class VehicleDeletionService {
    private final VehicleService vehicleService;

    public VehicleDeletionService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public String deleteVehicle(int vehicleId) {
        boolean isDeleted = vehicleService.deleteVehicle(vehicleId);
        if (isDeleted) {
            return "{\"message\": \"Vehicle deleted successfully!\"}";
        } else {
            return "{\"error\": \"Failed to delete vehicle.\"}";
        }
    }
}