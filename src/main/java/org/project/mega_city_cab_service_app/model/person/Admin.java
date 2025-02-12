package org.project.mega_city_cab_service_app.model.person;

import org.project.mega_city_cab_service_app.model.Parent.Person;

public class Admin extends Person {
    public Admin(String name, String address, String mobile, String  username , String password)
    {
        super(name, address, mobile,  username , password);
    }

    @Override
    public String getType() {
        return "ADMIN";
    }
}