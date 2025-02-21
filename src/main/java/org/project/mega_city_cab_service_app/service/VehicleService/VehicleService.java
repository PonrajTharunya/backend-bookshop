package org.project.mega_city_cab_service_app.service.VehicleService;

import org.project.mega_city_cab_service_app.dao.VehicleDAO;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;

import java.util.List;

public class VehicleService {
    private final VehicleDAO vehicleDAO;

    public VehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public boolean registerVehicle(Vehicle vehicle) {
        return vehicleDAO.addVehicle(vehicle);
    }

    public Vehicle getVehicle(int vehicleId) {
        return vehicleDAO.findVehicalByID(vehicleId);
    }

    public boolean updateVehicle(Vehicle vehicle) {
        return vehicleDAO.updateVehicle(vehicle);
    }

    public boolean deleteVehicle(int vehicleId) {
        return vehicleDAO.deleteVehicle(vehicleId);
    }

    public List<Vehicle> getVehiclesByType(String type) {
        return vehicleDAO.findVehiclesByType(type);
    }
}