
package org.project.mega_city_cab_service_app.repository.Vehicle;

import org.project.mega_city_cab_service_app.model.Parent.Vehicle;

public interface VehicleRepository {
    boolean save(Vehicle vehicle);
    Vehicle findByID(int vehicleId);
    boolean update(Vehicle vehicle);
    boolean delete(int vehicleId);
}