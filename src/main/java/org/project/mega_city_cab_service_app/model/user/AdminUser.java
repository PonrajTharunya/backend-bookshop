package org.project.mega_city_cab_service_app.model.user;

import org.project.mega_city_cab_service_app.model.Parent.User;

public class AdminUser extends User {
    public AdminUser(String name, String password) {
        super(name,password,"ADMIN");
    }
}