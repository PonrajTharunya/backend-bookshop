package org.project.mega_city_cab_service_app.dao;

import org.project.mega_city_cab_service_app.model.Parent.User;
import org.project.mega_city_cab_service_app.factory.UserFactory;

public class loginuserDAO {
    public User findByEmail(String email) {
        // Simulate database lookup
        if ("admin@example.com".equals(email)) {
            return UserFactory.createUser(email, "admin123", "ADMIN");
        } else if ("employee@example.com".equals(email)) {
            return UserFactory.createUser(email, "employee123", "EMPLOYEE");
        } else if ("customer@example.com".equals(email)) {
            return UserFactory.createUser(email, "customer123", "CUSTOMER");
        }
        return null; // User not found
    }
}