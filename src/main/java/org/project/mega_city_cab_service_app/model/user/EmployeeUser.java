package org.project.mega_city_cab_service_app.model.user;

import org.project.mega_city_cab_service_app.model.Parent.User;

public class EmployeeUser extends User {
    public EmployeeUser(String email, String password) {
        super(email,password,"EMPLOYEE");
    }
}

