package org.project.mega_city_cab_service_app.controller.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.mega_city_cab_service_app.dao.BookingDAO;
import org.project.mega_city_cab_service_app.service.BookingService.*;
import org.project.mega_city_cab_service_app.service.security.AccessDeniedException;
import org.project.mega_city_cab_service_app.service.security.AuthorizationService;
import org.project.mega_city_cab_service_app.util.JsonUtils;

import java.io.IOException;

@WebServlet(name = "BookingServlet", value = "/booking")
public class BookingServlet extends HttpServlet {

    private final BookingRegistrationService registrationService;
    private final BookingRetrievalService retrievalService;
    private final BookingUpdateService updateService;
    private final BookingDeletionService deletionService;

    public BookingServlet() {
        BookingService bookingService = new BookingService(new BookingDAO());
        BookingFactoryService factoryService = new BookingFactoryService();

        this.registrationService = new BookingRegistrationService(bookingService, factoryService);
        this.retrievalService = new BookingRetrievalService(bookingService);
        this.updateService = new BookingUpdateService(bookingService, factoryService);
        this.deletionService = new BookingDeletionService(bookingService);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Ensure only authorized roles can create bookings
            AuthorizationService.checkRole(req, "CUSTOMER","ADMIN");
            resp.setContentType("application/json");
            String jsonInput = readRequestBody(req);

            // Pass the raw JSON string to the service
            String response = registrationService.registerBooking(jsonInput);
            resp.getWriter().write(response);
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Ensure only authorized roles can retrieve booking details
            AuthorizationService.checkRole(req, "CUSTOMER", "ADMIN");

            resp.setContentType("application/json");

            // Check if the request is for a specific booking or all bookings
            String orderNoStr = req.getParameter("order_no");
            if (orderNoStr != null && !orderNoStr.isEmpty()) {
                // Retrieve a single booking by order number
                int orderNo = Integer.parseInt(orderNoStr);
                String response = retrievalService.getBooking(orderNo);
                resp.getWriter().write(response);
            } else {
                // Retrieve all bookings
                String response = retrievalService.getAllBookings();
                resp.getWriter().write(response);
            }
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\": \"Invalid order number format.\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Ensure only authorized roles can update bookings
            AuthorizationService.checkRole(req, "CUSTOMER", "ADMIN");

            resp.setContentType("application/json");
            String orderNoStr = req.getParameter("order_no");
            if (orderNoStr == null || orderNoStr.isEmpty()) {
                resp.getWriter().write("{\"error\": \"Order number is required in the URL parameter.\"}");
                return;
            }

            int orderNo = Integer.parseInt(orderNoStr);
            String jsonInput = readRequestBody(req);
            String response = updateService.updateBooking(orderNo, jsonInput);
            resp.getWriter().write(response);
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\": \"Invalid order number format.\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Ensure only authorized roles can delete bookings
            AuthorizationService.checkRole(req, "ADMIN");

            resp.setContentType("application/json");
            String orderNoStr = req.getParameter("order_no");
            if (orderNoStr == null || orderNoStr.isEmpty()) {
                resp.getWriter().write("{\"error\": \"Order number is required.\"}");
                return;
            }

            int orderNo = Integer.parseInt(orderNoStr);
            String response = deletionService.deleteBooking(orderNo);
            resp.getWriter().write(response);
        } catch (AccessDeniedException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\": \"Invalid order number format.\"}");
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