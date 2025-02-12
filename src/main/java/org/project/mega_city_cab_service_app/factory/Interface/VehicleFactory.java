package org.project.mega_city_cab_service_app.factory.Interface;

import org.project.mega_city_cab_service_app.model.Vehicle;

public interface VehicleFactory {
    Vehicle createVehicle(String jsonInput, String name, String model, String color, int year, int registrationNumber, int seatingCapacity );

}
