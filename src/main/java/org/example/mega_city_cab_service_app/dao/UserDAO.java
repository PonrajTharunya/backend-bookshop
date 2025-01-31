package org.example.mega_city_cab_service_app.dao;

import org.example.mega_city_cab_service_app.model.User;

public class UserDAO {
    // Hardcoded demo users (plaintext passwords)
    private static final User ADMIN = new User(
            "admin@gmail.com",
            "123", // Plaintext password
            "ADMIN"
    );

    private static final User EMPLOYEE = new User(
            "employee@gmail.com",
            "123", // Plaintext password
            "EMPLOYEE"
    );

    public User findByEmail(String email) {
        if (ADMIN.getEmail().equalsIgnoreCase(email)) return ADMIN;
        if (EMPLOYEE.getEmail().equalsIgnoreCase(email)) return EMPLOYEE;
        return null;
    }
}