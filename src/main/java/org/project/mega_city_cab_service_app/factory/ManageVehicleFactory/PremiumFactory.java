package org.project.mega_city_cab_service_app.factory.ManageVehicleFactory;

import org.project.mega_city_cab_service_app.factory.Interface.VehicleFactory;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.model.vehical.PremiumVehicle;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class PremiumFactory implements VehicleFactory {

    @Override
    public Vehicle createVehicle(String jsonInput, String name, String model, String color, int year, int registrationNumber, int seatingCapacity) {
        boolean hasWiFi=Boolean.parseBoolean(JsonUtils.extractValueFromJson(jsonInput, "hasWiFi"));
        return new PremiumVehicle(name, model, color, year, registrationNumber, seatingCapacity, hasWiFi);
    }
}
