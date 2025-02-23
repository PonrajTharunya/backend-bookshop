package org.project.mega_city_cab_service_app.controller.PlanPrice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.project.mega_city_cab_service_app.dao.PlanPrice.PlanPriceDAO;

import org.project.mega_city_cab_service_app.model.PlanPrice.PlanPrice;
import org.project.mega_city_cab_service_app.service.PlanPriceService.PlanPriceService;
import org.project.mega_city_cab_service_app.util.JsonUtils;

import java.io.IOException;

@WebServlet(name = "PlanPriceServlet", value = "/plan-price")
public class PlanPriceServlet extends HttpServlet {
    private final PlanPriceService planPriceService;

    public PlanPriceServlet() {
        this.planPriceService = new PlanPriceService(new PlanPriceDAO());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String jsonBody = readRequestBody(req);

        long distanceRangeId = Long.parseLong(JsonUtils.extractValueFromJson(jsonBody, "distanceRangeId"));
        double price = Double.parseDouble(JsonUtils.extractValueFromJson(jsonBody, "price"));
        double extraKmPrice = Double.parseDouble(JsonUtils.extractValueFromJson(jsonBody, "extraKmPrice"));
        double discount = Double.parseDouble(JsonUtils.extractValueFromJson(jsonBody, "discount"));
        int vehiclePlanId = Integer.parseInt(JsonUtils.extractValueFromJson(jsonBody, "vehiclePlanId"));

        PlanPrice planPrice = new PlanPrice(distanceRangeId, price, extraKmPrice, discount, vehiclePlanId);
        boolean isAdded = planPriceService.addPlanPrice(planPrice);

        if (isAdded) {
            resp.getWriter().write("{\"message\": \"Plan price added successfully!\"}");
        } else {
            resp.getWriter().write("{\"error\": \"Failed to add plan price.\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.getWriter().write(JsonUtils.toJson(planPriceService.getAllPlanPrices()));
        } else {
            try {
                long id = Long.parseLong(idStr);
                Object result = planPriceService.getPlanPriceById(id);
                if (result != null) {
                    resp.getWriter().write(JsonUtils.toJson(result));
                } else {
                    resp.getWriter().write("{\"error\": \"Plan price not found.\"}");
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

            long distanceRangeId = Long.parseLong(JsonUtils.extractValueFromJson(jsonBody, "distanceRangeId"));
            double price = Double.parseDouble(JsonUtils.extractValueFromJson(jsonBody, "price"));
            double extraKmPrice = Double.parseDouble(JsonUtils.extractValueFromJson(jsonBody, "extraKmPrice"));
            double discount = Double.parseDouble(JsonUtils.extractValueFromJson(jsonBody, "discount"));
            int vehiclePlanId = Integer.parseInt(JsonUtils.extractValueFromJson(jsonBody, "vehiclePlanId"));

            PlanPrice planPrice = new PlanPrice(distanceRangeId, price, extraKmPrice, discount, vehiclePlanId);
            planPrice.setId(id);

            boolean isUpdated = planPriceService.updatePlanPrice(planPrice);
            if (isUpdated) {
                resp.getWriter().write("{\"message\": \"Plan price updated successfully!\"}");
            } else {
                resp.getWriter().write("{\"error\": \"Failed to update plan price.\"}");
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
            boolean isDeleted = planPriceService.deletePlanPrice(id);
            if (isDeleted) {
                resp.getWriter().write("{\"message\": \"Plan price deleted successfully!\"}");
            } else {
                resp.getWriter().write("{\"error\": \"Failed to delete plan price.\"}");
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