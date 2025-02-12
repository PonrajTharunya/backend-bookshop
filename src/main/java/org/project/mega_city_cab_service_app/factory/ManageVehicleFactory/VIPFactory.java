package org.project.mega_city_cab_service_app.factory.ManageVehicleFactory;

import org.project.mega_city_cab_service_app.factory.Interface.VehicleFactory;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.model.vehical.VIPVehicle;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class VIPFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(String jsonInput, String name, String model, String color, int year, int registrationNumber, int seatingCapacity) {
        boolean hasChauffeurService= Boolean.parseBoolean(JsonUtils.extractValueFromJson(jsonInput, "hasChauffeurService"));
        return new VIPVehicle(name,model,color,year,registrationNumber,seatingCapacity,hasChauffeurService);
    }

}
