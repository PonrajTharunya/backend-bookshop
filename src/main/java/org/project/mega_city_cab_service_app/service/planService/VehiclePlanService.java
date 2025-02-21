package org.project.mega_city_cab_service_app.service.planService;

import org.project.mega_city_cab_service_app.model.VehiclePlan;
import org.project.mega_city_cab_service_app.repository.VehiclePlan.VehiclePlanRepository;


import java.util.List;

public class VehiclePlanService {
    private final VehiclePlanRepository vehiclePlanRepository;

    public VehiclePlanService(VehiclePlanRepository vehiclePlanRepository) {
        this.vehiclePlanRepository = vehiclePlanRepository;
    }

    public boolean addVehiclePlan(String planName) {
        VehiclePlan vehiclePlan = new VehiclePlan(planName);
        return vehiclePlanRepository.addVehiclePlan(vehiclePlan);
    }

    public List<VehiclePlan> getAllVehiclePlans() {
        return vehiclePlanRepository.getAllVehiclePlans();
    }

    public VehiclePlan getVehiclePlanById(int id) {
        return vehiclePlanRepository.getVehiclePlanById(id);
    }

    public boolean updateVehiclePlan(int id, String planName) {
        VehiclePlan vehiclePlan = vehiclePlanRepository.getVehiclePlanById(id);
        if (vehiclePlan != null) {
            vehiclePlan.setPlanName(planName);
            return vehiclePlanRepository.updateVehiclePlan(vehiclePlan);
        }
        return false;
    }

    public boolean deleteVehiclePlan(int id) {
        return vehiclePlanRepository.deleteVehiclePlan(id);
    }
}