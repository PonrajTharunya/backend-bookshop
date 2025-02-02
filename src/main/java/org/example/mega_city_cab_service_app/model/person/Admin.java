package org.example.mega_city_cab_service_app.model.person;

import org.example.mega_city_cab_service_app.model.Person;

public class Admin extends Person {
    public Admin(String name, String address, String mobile)
    {
        super(name, address, mobile);
    }

    @Override
    public String getType() {
        return "ADMIN";
    }
}