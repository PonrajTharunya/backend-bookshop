package org.example.mega_city_cab_service_app.model.user;

import org.example.mega_city_cab_service_app.model.User;

public class AdminUser extends User {
    public AdminUser(String name, String password) {
        super(name,password,"ADMIN");
    }
}