package org.project.mega_city_cab_service_app.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.project.mega_city_cab_service_app.dao.DistanceRange.DistanceRangeDAO;

import org.project.mega_city_cab_service_app.model.Range.DistanceRange;
import org.project.mega_city_cab_service_app.service.distanceRange.DistanceRangeService;
import org.project.mega_city_cab_service_app.util.JsonUtils;

import java.io.IOException;

@WebServlet(name = "DistanceRangeServlet", value = "/distance-range")
public class DistanceRangeServlet extends HttpServlet {
    private final DistanceRangeService distanceRangeService;

    public DistanceRangeServlet() {
        this.distanceRangeService = new DistanceRangeService(new DistanceRangeDAO());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String jsonBody = readRequestBody(req);

        int minDistance = Integer.parseInt(JsonUtils.extractValueFromJson(jsonBody, "minDistance"));
        int maxDistance = Integer.parseInt(JsonUtils.extractValueFromJson(jsonBody, "maxDistance"));

        DistanceRange distanceRange = new DistanceRange(minDistance, maxDistance);
        boolean isAdded = distanceRangeService.addDistanceRange(distanceRange);

        if (isAdded) {
            resp.getWriter().write("{\"message\": \"Distance range added successfully!\"}");
        } else {
            resp.getWriter().write("{\"error\": \"Failed to add distance range.\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.getWriter().write(JsonUtils.toJson(distanceRangeService.getAllDistanceRanges()));
        } else {
            try {
                long id = Long.parseLong(idStr);
                Object result = distanceRangeService.getDistanceRangeById(id);
                if (result != null) {
                    resp.getWriter().write(JsonUtils.toJson(result));
                } else {
                    resp.getWriter().write("{\"error\": \"Distance range not found.\"}");
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
            long id = Long.parseLong(idStr);
            String jsonBody = readRequestBody(req);

            int minDistance = Integer.parseInt(JsonUtils.extractValueFromJson(jsonBody, "minDistance"));
            int maxDistance = Integer.parseInt(JsonUtils.extractValueFromJson(jsonBody, "maxDistance"));

            DistanceRange distanceRange = new DistanceRange(minDistance, maxDistance);
            distanceRange.setId(id);

            boolean isUpdated = distanceRangeService.updateDistanceRange(distanceRange);
            if (isUpdated) {
                resp.getWriter().write("{\"message\": \"Distance range updated successfully!\"}");
            } else {
                resp.getWriter().write("{\"error\": \"Failed to update distance range.\"}");
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
            long id = Long.parseLong(idStr);
            boolean isDeleted = distanceRangeService.deleteDistanceRange(id);
            if (isDeleted) {
                resp.getWriter().write("{\"message\": \"Distance range deleted successfully!\"}");
            } else {
                resp.getWriter().write("{\"error\": \"Failed to delete distance range.\"}");
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