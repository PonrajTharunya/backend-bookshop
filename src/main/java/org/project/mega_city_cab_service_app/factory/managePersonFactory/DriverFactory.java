package org.project.mega_city_cab_service_app.factory.managePersonFactory;

import org.project.mega_city_cab_service_app.factory.Interface.PersonFactory;
import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.model.person.Driver;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class DriverFactory implements PersonFactory {
    public Person createPerson(String jsonInput, String name, String address, String mobile,String username, String password ) {
        double salary = Double.parseDouble(JsonUtils.extractValueFromJson(jsonInput, "salary"));
        int experience = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "experience"));
        String licenseNumber = JsonUtils.extractValueFromJson(jsonInput, "licenseNumber");
        String licenseType = JsonUtils.extractValueFromJson(jsonInput, "licenseType");
        return new Driver(name,address,mobile,username,password,salary,experience,licenseNumber,licenseType);
    }
}
