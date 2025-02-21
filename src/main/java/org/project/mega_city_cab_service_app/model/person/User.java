package org.project.mega_city_cab_service_app.model.person;

import org.project.mega_city_cab_service_app.model.Parent.Person;

public class User extends Person {
    private final String role;

    public User(String name, String address, String mobile, String username, String password, String role) {
        super(name, address, mobile, username, password);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getType() {
        return "";
    }
}