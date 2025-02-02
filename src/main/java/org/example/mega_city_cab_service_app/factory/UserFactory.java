package org.example.mega_city_cab_service_app.factory;

import org.example.mega_city_cab_service_app.model.user.AdminUser;
import org.example.mega_city_cab_service_app.model.user.CustomerUser;
import org.example.mega_city_cab_service_app.model.user.EmployeeUser;
import org.example.mega_city_cab_service_app.model.User;

public class UserFactory {
    public static User createUser(String email, String password, String role) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                return new AdminUser(email, password);
            case "EMPLOYEE":
                return new EmployeeUser(email, password);
            case "CUSTOMER":
                return new CustomerUser(email, password);
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}