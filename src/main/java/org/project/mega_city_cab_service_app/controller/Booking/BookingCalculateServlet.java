package org.project.mega_city_cab_service_app.controller.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.mega_city_cab_service_app.service.BaseBookingCalculatorService.BookingCalculateService;
import org.project.mega_city_cab_service_app.service.security.AccessDeniedException;
import org.project.mega_city_cab_service_app.service.security.AuthorizationService;
import org.project.mega_city_cab_service_app.util.JsonUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "BookingCalculateServlet", value = "/booking/calculate")
public class BookingCalculateServlet extends HttpServlet {

    private final BookingCalculateService calculateService;

    public BookingCalculateServlet() {
        this.calculateService = new BookingCalculateService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Ensure only authorized roles can calculate the booking amount
            AuthorizationService.checkRole(req, "CUSTOMER", "ADMIN");

            resp.setContentType("application/json");

            // Extract required parameters from the request body
            StringBuilder buffer = new StringBuilder();
            String line;
            try (var reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
            }
            String jsonInput = buffer.toString();

            // Parse JSON input
            int orderId = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "order_id"));
            int actualKmUsed = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "actual_km_used"));
            String returnDateStr = JsonUtils.extractValueFromJson(jsonInput, "return_date");
            LocalDateTime returnDate = LocalDateTime.parse(returnDateStr);

            // Parse additional input data (e.g., driverFee)
            Map<String, Object> inputData = new HashMap<>();
            String driverFeeStr = JsonUtils.extractValueFromJson(jsonInput, "driverFee");
            if (driverFeeStr != null && !driverFeeStr.isEmpty()) {
                inputData.put("driverFee", Double.parseDouble(driverFeeStr));
            }

            // Validate inputs
            if (orderId <= 0 || actualKmUsed < 0 || returnDate == null) {
                resp.getWriter().write("{\"error\": \"Invalid input parameters.\"}");
                return;
            }

            // Calculate the final amount
            String response = calculateService.calculateFinalAmount(orderId, actualKmUsed, returnDate, inputData);

            // Write the response
            resp.getWriter().write(response);
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (IllegalArgumentException e) {
            resp.getWriter().write("{\"error\": \"Invalid number format or missing fields.\"}");
        } catch (Exception e) {
            resp.getWriter().write("{\"error\": \"An unexpected error occurred.\"}");
            e.printStackTrace();
        }
    }
}