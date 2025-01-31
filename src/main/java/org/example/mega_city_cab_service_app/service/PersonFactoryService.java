package org.example.mega_city_cab_service_app.service;
import org.example.mega_city_cab_service_app.factory.CustomerFactory;
import org.example.mega_city_cab_service_app.factory.PersonFactory;
import org.example.mega_city_cab_service_app.factory.FactoryRegistry;
import org.example.mega_city_cab_service_app.factory.EmployeeFactory;
import org.example.mega_city_cab_service_app.util.JsonUtils;

public class PersonFactoryService {
    public PersonFactory getFactory(String type, String jsonInput) {
        PersonFactory factory = FactoryRegistry.getFactory(type);
        if (factory == null) {
            return null;
        }

        // Handle EmployeeFactory separately
        if (type.equalsIgnoreCase("EMPLOYEE")) {
            double salary = Double.parseDouble(JsonUtils.extractValueFromJson(jsonInput, "salary"));
            int experience = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "experience"));
            factory = new EmployeeFactory(salary, experience);
        }
        // this for customer
        else if (type.equalsIgnoreCase("CUSTOMER")) {
            int rating = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "rating"));
            String description = JsonUtils.extractValueFromJson(jsonInput, "description");
            factory = new CustomerFactory(rating, description);
        }
        return factory;
    }
}