package org.project.mega_city_cab_service_app.factory.ManageVehicleFactory;

import org.project.mega_city_cab_service_app.factory.Interface.VehicleFactory;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.model.vehical.RegularVehicle;

public class RegularFactory implements VehicleFactory {

    @Override
    public Vehicle createVehicle(String jsonInput, String name, String model, String color, int year, int registrationNumber, int seatingCapacity) {
        return new RegularVehicle(name, model, color, year, registrationNumber, seatingCapacity);
    }
}


