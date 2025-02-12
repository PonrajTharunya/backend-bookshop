package org.project.mega_city_cab_service_app.factory;

import org.project.mega_city_cab_service_app.model.user.AdminUser;
import org.project.mega_city_cab_service_app.model.user.CustomerUser;
import org.project.mega_city_cab_service_app.model.user.EmployeeUser;
import org.project.mega_city_cab_service_app.model.Parent.User;

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