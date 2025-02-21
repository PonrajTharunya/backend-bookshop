package org.project.mega_city_cab_service_app.controller.Vehicle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.mega_city_cab_service_app.dao.VehicleDAO;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.model.vehical.PremiumVehicle;
import org.project.mega_city_cab_service_app.model.vehical.VIPVehicle;
import org.project.mega_city_cab_service_app.service.VehicleService.*;
import org.project.mega_city_cab_service_app.service.security.AccessDeniedException;
import org.project.mega_city_cab_service_app.service.security.AuthorizationService;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "VehicleServlet", value = "/vehicle")
public class VehicleServlet extends HttpServlet {

    private final VehicleService vehicleService = new VehicleService(new VehicleDAO(DBConnection.getInstance()));
    private final VehicleFactoryService vehicleFactoryService = new VehicleFactoryService();
    private final VehicleRegistrationService registrationService = new VehicleRegistrationService(vehicleService, vehicleFactoryService);
    private final VehicleRetrievalService retrievalService = new VehicleRetrievalService(vehicleService);
    private final VehicleUpdateService updateService = new VehicleUpdateService(vehicleService, vehicleFactoryService);
    private final VehicleDeletionService deletionService = new VehicleDeletionService(vehicleService);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Ensure only authorized roles can register vehicles
            AuthorizationService.checkRole(req, "ADMIN", "EMPLOYEE");

            resp.setContentType("application/json");
            String jsonInput = readRequestBody(req);
            String response = registrationService.registerVehicle(jsonInput);
            resp.getWriter().write(response);
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Ensure only authorized roles can retrieve vehicle details
            AuthorizationService.checkRole(req, "ADMIN");

            resp.setContentType("application/json");

            // Check if the request contains an 'id' parameter
            String vehicleIdStr = req.getParameter("id");
            String vehicleType = req.getParameter("type");

            if (vehicleIdStr != null && !vehicleIdStr.isEmpty()) {
                // Retrieve a single vehicle by ID
                try {
                    int vehicleId = Integer.parseInt(vehicleIdStr);
                    String response = retrievalService.getVehicle(vehicleId);
                    resp.getWriter().write(response);
                } catch (NumberFormatException e) {
                    resp.getWriter().write("{\"error\": \"Invalid vehicle ID format.\"}");
                }
            } else if (vehicleType != null && !vehicleType.isEmpty()) {
                // Retrieve all vehicles by type
                List<Vehicle> vehicles = vehicleService.getVehiclesByType(vehicleType);

                if (vehicles.isEmpty()) {
                    resp.getWriter().write("{\"error\": \"No vehicles found for the given type.\"}");
                    return;
                }

                StringBuilder jsonResponse = new StringBuilder("[");
                for (Vehicle vehicle : vehicles) {
                    jsonResponse.append("{")
                            .append("\"id\": \"").append(vehicle.getId()).append("\", ")
                            .append("\"name\": \"").append(vehicle.getName()).append("\", ")
                            .append("\"model\": \"").append(vehicle.getModel()).append("\", ")
                            .append("\"color\": \"").append(vehicle.getColor()).append("\", ")
                            .append("\"year\": ").append(vehicle.getYear()).append(", ")
                            .append("\"registration_number\": ").append(vehicle.getRegistrationNumber()).append(", ")
                            .append("\"seating_capacity\": ").append(vehicle.getSeatingCapacity()).append(", ")
                            .append("\"type\": \"").append(vehicle.getType()).append("\"");

                    // Add specific fields for Premium and VIP vehicles
                    if (vehicle instanceof PremiumVehicle premiumVehicle) {
                        jsonResponse.append(", \"has_wifi\": ").append(premiumVehicle.hasWiFi());
                    } else if (vehicle instanceof VIPVehicle vipVehicle) {
                        jsonResponse.append(", \"has_chauffeur_service\": ").append(vipVehicle.hasChauffeurService());
                    }

                    jsonResponse.append("},");
                }

                if (!vehicles.isEmpty()) {
                    jsonResponse.deleteCharAt(jsonResponse.length() - 1); // Remove trailing comma
                }
                jsonResponse.append("]");
                resp.getWriter().write(jsonResponse.toString());
            } else {
                // If neither 'id' nor 'type' is provided, return an error
                resp.getWriter().write("{\"error\": \"Either 'id' or 'type' parameter is required.\"}");
            }
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Ensure only authorized roles can update vehicles
            AuthorizationService.checkRole(req, "ADMIN", "EMPLOYEE");

            resp.setContentType("application/json");
            String originalVehicleIdStr = req.getParameter("id");
            if (originalVehicleIdStr == null || originalVehicleIdStr.isEmpty()) {
                resp.getWriter().write("{\"error\": \"Original vehicle ID is required in the URL parameter.\"}");
                return;
            }

            int originalVehicleId = Integer.parseInt(originalVehicleIdStr);
            String jsonInput = readRequestBody(req);
            String response = updateService.updateVehicle(originalVehicleId, jsonInput);
            resp.getWriter().write(response);
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\": \"Invalid vehicle ID format.\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Ensure only authorized roles can delete vehicles
            AuthorizationService.checkRole(req, "ADMIN");

            resp.setContentType("application/json");
            String vehicleIdStr = req.getParameter("id");
            if (vehicleIdStr == null || vehicleIdStr.isEmpty()) {
                resp.getWriter().write("{\"error\": \"Vehicle ID is required.\"}");
                return;
            }

            int vehicleId = Integer.parseInt(vehicleIdStr);
            String response = deletionService.deleteVehicle(vehicleId);
            resp.getWriter().write(response);
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\": \"Invalid vehicle ID format.\"}");
        }
    }

    private String readRequestBody(HttpServletRequest req) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        try (var reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        }
        return buffer.toString();
    }
}