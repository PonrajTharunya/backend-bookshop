    package org.project.mega_city_cab_service_app.controller.Tax;

    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.project.mega_city_cab_service_app.dao.TaxDAO;

    import org.project.mega_city_cab_service_app.service.TaxService.*;
    import org.project.mega_city_cab_service_app.service.security.AccessDeniedException;
    import org.project.mega_city_cab_service_app.service.security.AuthorizationService;
    import org.project.mega_city_cab_service_app.util.JsonUtils;

    import java.io.IOException;

    @WebServlet(name = "TaxServlet", value = "/tax")
    public class TaxServlet extends HttpServlet {

        private final TaxRegistrationService registrationService;
        private final TaxRetrievalService retrievalService;
        private final TaxUpdateService updateService;
        private final TaxDeletionService deletionService;

        public TaxServlet() {
            TaxService taxService = new TaxService(new TaxDAO());
            TaxFactoryService factoryService = new TaxFactoryService();
            this.registrationService = new TaxRegistrationService(taxService, factoryService);
            this.retrievalService = new TaxRetrievalService(taxService);
            this.updateService = new TaxUpdateService(taxService, factoryService);
            this.deletionService = new TaxDeletionService(taxService);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try {
                // Ensure only authorized roles can create taxes
                AuthorizationService.checkRole(req, "ADMIN");

                resp.setContentType("application/json");
                String jsonInput = readRequestBody(req);

                // Register the tax using the TaxRegistrationService
                String response = registrationService.registerTax(jsonInput);
                resp.getWriter().write(response);
            } catch (AccessDeniedException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try {
                // Ensure only authorized roles can retrieve tax details
                AuthorizationService.checkRole(req, "CUSTOMER", "ADMIN");
                resp.setContentType("application/json");
                String taxIdStr = req.getParameter("tax_id");
                if (taxIdStr == null || taxIdStr.isEmpty()) {
                    // If no tax ID is provided, retrieve all taxes
                    String response = retrievalService.getAllTaxes();
                    resp.getWriter().write(response);
                } else {
                    try {
                        // Parse the tax ID and retrieve the specific tax
                        int taxId = Integer.parseInt(taxIdStr);
                        String response = retrievalService.getTaxById(taxId);
                        resp.getWriter().write(response);
                    } catch (NumberFormatException e) {
                        // Handle invalid tax ID format
                        resp.getWriter().write("{\"error\": \"Invalid tax ID format.\"}");
                    }
                }
            } catch (AccessDeniedException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }
        }

        @Override
        protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try {
                // Ensure only authorized roles can update taxes
                AuthorizationService.checkRole(req, "ADMIN");

                resp.setContentType("application/json");
                String taxIdStr = req.getParameter("tax_id");
                if (taxIdStr == null || taxIdStr.isEmpty()) {
                    resp.getWriter().write("{\"error\": \"Tax ID is required in the URL parameter.\"}");
                    return;
                }

                try {
                    // Parse the tax ID
                    int taxId = Integer.parseInt(taxIdStr);

                    // Read JSON input from the request body
                    String jsonInput = readRequestBody(req);

                    // Update the tax using the TaxUpdateService
                    String response = updateService.updateTax(taxId, jsonInput);

                    // Write the response back to the client
                    resp.getWriter().write(response);
                } catch (NumberFormatException e) {
                    // Handle invalid tax ID format
                    resp.getWriter().write("{\"error\": \"Invalid tax ID format.\"}");
                }
            } catch (AccessDeniedException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }
        }

        @Override
        protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try {
                // Ensure only authorized roles can delete taxes
                AuthorizationService.checkRole(req, "ADMIN");

                resp.setContentType("application/json");
                String taxIdStr = req.getParameter("tax_id");
                if (taxIdStr == null || taxIdStr.isEmpty()) {
                    resp.getWriter().write("{\"error\": \"Tax ID is required.\"}");
                    return;
                }

                try {
                    // Parse the tax ID
                    int taxId = Integer.parseInt(taxIdStr);

                    // Delete the tax using the TaxDeletionService
                    String response = deletionService.deleteTax(taxId);

                    // Write the response back to the client
                    resp.getWriter().write(response);
                } catch (NumberFormatException e) {
                    // Handle invalid tax ID format
                    resp.getWriter().write("{\"error\": \"Invalid tax ID format.\"}");
                }
            } catch (AccessDeniedException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }
        }

        /**
         * Helper method to read the request body as a JSON string.
         */
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