//package org.example.mega_city_cab_service_app.service;
//
//import org.example.mega_city_cab_service_app.factory.PersonFactory;
//import org.example.mega_city_cab_service_app.factory.managePersonFactory.CustomerFactory;
//import org.example.mega_city_cab_service_app.factory.FactoryRegistry;
//import org.example.mega_city_cab_service_app.factory.managePersonFactory.EmployeeFactory;
//import org.example.mega_city_cab_service_app.util.JsonUtils;
//
//public class PersonFactoryService {
//    public PersonFactory getFactory(String type, String jsonInput) {
//        PersonFactory factory = FactoryRegistry.getFactory(type);
//        if (factory == null) {
//            return null;
//        }
//
//        // Handle EmployeeFactory separately
//        if (type.equalsIgnoreCase("EMPLOYEE")) {
//            double salary = Double.parseDouble(JsonUtils.extractValueFromJson(jsonInput, "salary"));
//            int experience = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "experience"));
//            factory = new EmployeeFactory(salary, experience);
//        }
//        // this for customer
//        else if (type.equalsIgnoreCase("CUSTOMER")) {
//            int rating = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "rating"));
//            String description = JsonUtils.extractValueFromJson(jsonInput, "description");
//            factory = new CustomerFactory(rating, description);
//        }
//        return factory;
//    }
//}
package org.project.mega_city_cab_service_app.service.PersonService;
import org.project.mega_city_cab_service_app.factory.Registry.PersonFactoryRegistry;
import org.project.mega_city_cab_service_app.factory.Interface.PersonFactory;
import org.project.mega_city_cab_service_app.factory.managePersonFactory.AdminFactory;
import org.project.mega_city_cab_service_app.factory.managePersonFactory.CustomerFactory;
import org.project.mega_city_cab_service_app.factory.managePersonFactory.DriverFactory;
import org.project.mega_city_cab_service_app.factory.managePersonFactory.EmployeeFactory;
/*
public class PersonFactoryService {
    public PersonFactory getFactory(String type, String jsonInput) {
        if (type == null) return null;

        switch (type.toUpperCase()) {
            case "ADMIN":
                return new AdminFactory();
            case "EMPLOYEE":
                double salary = Double.parseDouble(JsonUtils.extractValueFromJson(jsonInput, "salary"));
                int experience = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "experience"));
                return new EmployeeFactory(salary, experience);
            case "CUSTOMER":
                int rating = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "rating"));
                String description = JsonUtils.extractValueFromJson(jsonInput, "description");
                return new CustomerFactory(rating, description);
            default:
                return null;
        }
    }
}*/

public class PersonFactoryService {

    public PersonFactoryService() {
        // Register default factories during initialization
        PersonFactoryRegistry.registerFactory("ADMIN", new AdminFactory());
        PersonFactoryRegistry.registerFactory("EMPLOYEE", new EmployeeFactory());
        PersonFactoryRegistry.registerFactory("CUSTOMER", new CustomerFactory());
        PersonFactoryRegistry.registerFactory("DRIVER", new DriverFactory());
    }

    public PersonFactory getFactory(String type) {
        PersonFactory factory = PersonFactoryRegistry.getFactory(type);
        if (factory == null) {
            throw new IllegalArgumentException("Invalid person type: " + type);
        }
        return factory;
    }

}