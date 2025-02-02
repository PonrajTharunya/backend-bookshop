package org.project.mega_city_cab_service_app.service.security;

import jakarta.servlet.http.HttpServletRequest;

public class AuthorizationService {
    public static void checkRole(HttpServletRequest req, String... allowedRoles)
            throws AccessDeniedException {
        String userRole = (String) req.getSession().getAttribute("role");

        if (userRole == null) {
            throw new AccessDeniedException("User not authenticated");
        }

        for (String role : allowedRoles) {
            if (role.equalsIgnoreCase(userRole)) {
                return;
            }
        }
        throw new AccessDeniedException("Insufficient privileges");
    }
}