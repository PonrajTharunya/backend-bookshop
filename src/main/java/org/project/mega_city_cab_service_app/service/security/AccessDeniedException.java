package org.project.mega_city_cab_service_app.service.security;

public class AccessDeniedException extends Exception {
    public AccessDeniedException(String message) {
        super(message);
    }
}