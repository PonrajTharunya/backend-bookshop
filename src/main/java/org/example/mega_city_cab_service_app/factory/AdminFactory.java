package org.example.mega_city_cab_service_app.factory;

import org.example.mega_city_cab_service_app.model.Admin;
import org.example.mega_city_cab_service_app.model.Person;

public class AdminFactory implements PersonFactory {
    @Override
    public Person createPerson(String name, String address, String mobile) {
        return new Admin(name, address, mobile);
    }
}





