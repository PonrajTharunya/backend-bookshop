package org.project.mega_city_cab_service_app.service.VehicleService;

import org.project.mega_city_cab_service_app.factory.Interface.VehicleFactory;
import org.project.mega_city_cab_service_app.factory.ManageVehicleFactory.PremiumFactory;
import org.project.mega_city_cab_service_app.factory.ManageVehicleFactory.RegularFactory;
import org.project.mega_city_cab_service_app.factory.ManageVehicleFactory.VIPFactory;
import org.project.mega_city_cab_service_app.factory.Registry.VehicleFactoryRegistry;

public class VehicleFactoryService {
    public VehicleFactoryService() {
        VehicleFactoryRegistry.RegisterVehicleFactory("PREMIUM", new PremiumFactory());
        VehicleFactoryRegistry.RegisterVehicleFactory("REGULAR", new RegularFactory());
        VehicleFactoryRegistry.RegisterVehicleFactory("VIP",new VIPFactory());
    }

    public VehicleFactory getVehicleFactory(String vehicleType) {
        VehicleFactory factory = VehicleFactoryRegistry.GetVehicleFactory(vehicleType);
        if (factory == null) {
            throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        }
        return factory;
    }

}
