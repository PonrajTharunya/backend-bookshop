package org.project.mega_city_cab_service_app.service.VehicleService;

import org.project.mega_city_cab_service_app.dao.VehicleDAO;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;

public class VehicleService {
    private final VehicleDAO vehicleDAO;

//constructor (dependency injection)=PersonService class does not create or manage its own dependencies
    public VehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO; }

    public boolean registerVehicle(Vehicle vehicle) {
        return vehicleDAO.addVehicle(vehicle);
    }

}
