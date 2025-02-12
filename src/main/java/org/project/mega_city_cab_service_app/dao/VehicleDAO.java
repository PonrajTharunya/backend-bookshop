package org.project.mega_city_cab_service_app.dao;

import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.model.vehical.PremiumVehicle;
import org.project.mega_city_cab_service_app.model.vehical.VIPVehicle;

import java.util.HashMap;
import java.util.Map;

public class VehicleDAO {
    //access modifier,final modifier, type declaration, field name, initialization
    private final Map<String, Vehicle> vehicleMap=new HashMap<>();
//here keys are String bcz objects representing the vehicle type and the values are Vehicle objects.
    //It tells the compiler that the addVehicle method expects an object of type Vehicle 2. name of variable that hold the vehicle
    public boolean addVehicle(Vehicle vehicle) {
        System.out.println("serving Vehicle Type : "+vehicle.getType());
        System.out.println("Vehicle Model : "+vehicle.getModel());
        System.out.println("Vehicle Color : "+vehicle.getColor());
        System.out.println("Vehicle year : "+vehicle.getYear());
        System.out.println("Vehicle RegistrationNumber : "+vehicle.getRegistrationNumber());
        System.out.println("Vehicle seatingCapacity: "+vehicle.getSeatingCapacity());


        if (vehicle instanceof PremiumVehicle){
            PremiumVehicle pv = (PremiumVehicle) vehicle;
            System.out.println("hasWiFi : "+pv.hasWiFi());
        }
        else if (vehicle instanceof VIPVehicle){
            VIPVehicle vip = (VIPVehicle) vehicle;
            System.out.println("hasChauffeurService : "+vip.hasChauffeurService());
        }
        //store in to map
        //string object //key and value//object
        vehicleMap.put(vehicle.getType(), vehicle);
        return true;
    }



}
