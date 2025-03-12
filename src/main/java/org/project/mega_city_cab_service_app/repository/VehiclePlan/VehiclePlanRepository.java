package org.project.mega_city_cab_service_app.repository.VehiclePlan;

import org.project.mega_city_cab_service_app.model.Plan.VehiclePlan;

import java.util.List;

public interface VehiclePlanRepository {
    boolean addVehiclePlan(VehiclePlan vehiclePlan);
    List<VehiclePlan> getAllVehiclePlans();
    VehiclePlan getVehiclePlanById(int id);
    boolean updateVehiclePlan(VehiclePlan vehiclePlan);
    boolean deleteVehiclePlan(int id);
}