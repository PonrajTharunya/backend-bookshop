package org.project.mega_city_cab_service_app.model.user;

import org.project.mega_city_cab_service_app.model.User;

public class CustomerUser extends User {
    public CustomerUser(String email, String password) {
        super(email,password,"CUSTOMER");
    }
}
