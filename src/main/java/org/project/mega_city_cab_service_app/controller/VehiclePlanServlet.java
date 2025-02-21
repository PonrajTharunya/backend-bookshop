package org.project.mega_city_cab_service_app.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.project.mega_city_cab_service_app.dao.VehiclePlan.VehiclePlanDAO;
import org.project.mega_city_cab_service_app.service.planService.VehiclePlanService;
import org.project.mega_city_cab_service_app.util.JsonUtils;

import java.io.IOException;

@WebServlet(name = "VehiclePlanServlet", value = "/vehicle-plan")
public class VehiclePlanServlet extends HttpServlet {
    private final VehiclePlanService vehiclePlanService;

    public VehiclePlanServlet() {
        this.vehiclePlanService = new VehiclePlanService(new VehiclePlanDAO());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String jsonBody = readRequestBody(req);

        String planName = JsonUtils.extractValueFromJson(jsonBody, "planName");
        if (planName == null || planName.isEmpty()) {
            resp.getWriter().write("{\"error\": \"Invalid or missing planName.\"}");
            return;
        }

        boolean isAdded = vehiclePlanService.addVehiclePlan(planName);
        if (isAdded) {
            resp.getWriter().write("{\"message\": \"Vehicle plan added successfully!\"}");
        } else {
            resp.getWriter().write("{\"error\": \"Failed to add vehicle plan.\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.getWriter().write(JsonUtils.toJson(vehiclePlanService.getAllVehiclePlans()));
        } else {
            try {
                int id = Integer.parseInt(idStr);
                Object result = vehiclePlanService.getVehiclePlanById(id);
                if (result != null) {
                    resp.getWriter().write(JsonUtils.toJson(result));
                } else {
                    resp.getWriter().write("{\"error\": \"Vehicle plan not found.\"}");
                }
            } catch (NumberFormatException e) {
                resp.getWriter().write("{\"error\": \"Invalid ID format.\"}");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.getWriter().write("{\"error\": \"ID is required in the URL parameter.\"}");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            String planName = JsonUtils.extractValueFromJson(readRequestBody(req), "planName");

            boolean isUpdated = vehiclePlanService.updateVehiclePlan(id, planName);
            if (isUpdated) {
                resp.getWriter().write("{\"message\": \"Vehicle plan updated successfully!\"}");
            } else {
                resp.getWriter().write("{\"error\": \"Failed to update vehicle plan.\"}");
            }
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\": \"Invalid ID format.\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.getWriter().write("{\"error\": \"ID is required.\"}");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            boolean isDeleted = vehiclePlanService.deleteVehiclePlan(id);
            if (isDeleted) {
                resp.getWriter().write("{\"message\": \"Vehicle plan deleted successfully!\"}");
            } else {
                resp.getWriter().write("{\"error\": \"Failed to delete vehicle plan.\"}");
            }
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\": \"Invalid ID format.\"}");
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