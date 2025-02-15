package org.project.mega_city_cab_service_app.factory.Registry;

import org.project.mega_city_cab_service_app.factory.Interface.VehicleFactory;

import java.util.HashMap;
import java.util.Map;

public class VehicleFactoryRegistry {
    private static final Map<String, VehicleFactory> vehicleFactoryRegistry = new HashMap<>();
    public static void RegisterVehicleFactory(String vehicleType, VehicleFactory vehicleFactory) {
        vehicleFactoryRegistry.put(vehicleType.toUpperCase(), vehicleFactory);
    }
    public static VehicleFactory GetVehicleFactory(String vehicleType) {
        return vehicleFactoryRegistry.get(vehicleType.toUpperCase());
    }
    public static void clear(){
        vehicleFactoryRegistry.clear();
    }
}
