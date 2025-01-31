package org.example.mega_city_cab_service_app.model;

public class User {
    private final String email;
    private final String password; // Plaintext password
    private final String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}