package org.project.mega_city_cab_service_app.controller.Vehicle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.mega_city_cab_service_app.dao.VehicleDAO;
import org.project.mega_city_cab_service_app.service.VehicleService.*;
import org.project.mega_city_cab_service_app.service.security.AccessDeniedException;
import org.project.mega_city_cab_service_app.service.security.AuthorizationService;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.io.IOException;

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
            String vehicleIdStr = req.getParameter("id");
            if (vehicleIdStr == null || vehicleIdStr.isEmpty()) {
                resp.getWriter().write("{\"error\": \"Vehicle ID is required.\"}");
                return;
            }

            int vehicleId = Integer.parseInt(vehicleIdStr);
            String response = retrievalService.getVehicle(vehicleId);
            resp.getWriter().write(response);
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\": \"Invalid vehicle ID format.\"}");
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