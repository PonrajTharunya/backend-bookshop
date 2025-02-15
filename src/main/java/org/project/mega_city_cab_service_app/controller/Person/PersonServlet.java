    //
    //package org.example.mega_city_cab_service_app.controller;
    //
    //import org.example.mega_city_cab_service_app.dao.PersonDAO;


    //import org.example.mega_city_cab_service_app.factory.PersonFactory;
    //import org.example.mega_city_cab_service_app.factory.FactoryRegistry;
    //import org.example.mega_city_cab_service_app.model.Person;
    //import org.example.mega_city_cab_service_app.service.PersonService;
    //import org.example.mega_city_cab_service_app.util.JsonUtils;
    //
    //import jakarta.servlet.ServletException;
    //import jakarta.servlet.annotation.WebServlet;
    //import jakarta.servlet.http.HttpServlet;
    //import jakarta.servlet.http.HttpServletRequest;
    //import jakarta.servlet.http.HttpServletResponse;
    //import java.io.BufferedReader;
    //import java.io.IOException;
    //
    //@WebServlet(name = "PersonServlet", value = "/registerPerson")
    //public class PersonServlet extends HttpServlet {
    //    private PersonService personService;
    //
    //    @Override
    //    public void init() throws ServletException {
    //        super.init();
    //        this.personService = new PersonService(new PersonDAO());
    //
    //        // Register factories during initialization
    //        FactoryRegistry.registerFactory("ADMIN", new AdminFactory());
    //        FactoryRegistry.registerFactory("EMPLOYEE", (name, address, mobile) -> {
    //            // This lambda is a placeholder; actual parameters will be handled in doPost
    //            return null;
    //        });
    //    }
    //
    //    @Override
    //    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //        resp.setContentType("application/json");
    //
    //        // Read JSON input
    //        String jsonInput = readRequestBody(req);
    //        String type = JsonUtils.extractValueFromJson(jsonInput, "type");
    //        String name = JsonUtils.extractValueFromJson(jsonInput, "name");
    //        String address = JsonUtils.extractValueFromJson(jsonInput, "address");
    //        String mobile = JsonUtils.extractValueFromJson(jsonInput, "mobile");
    //
    //        if (type == null || name == null || address == null || mobile == null) {
    //            resp.getWriter().write("{\"error\": \"Missing required fields in JSON input.\"}");
    //            return;
    //        }
    //
    //        // Get the factory from the registry
    //        PersonFactory personFactory = FactoryRegistry.getFactory(type);
    //        if (personFactory == null) {
    //            resp.getWriter().write("{\"error\": \"Invalid person type.\"}");
    //            return;
    //        }
    //
    //        // Handle EmployeeFactory separately
    //        if (type.equalsIgnoreCase("EMPLOYEE")) {
    //            double salary = Double.parseDouble(JsonUtils.extractValueFromJson(jsonInput, "salary"));
    //            int experience = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "experience"));
    //            personFactory = new EmployeeFactory(salary, experience);
    //        }
    //
    //        // Create the person object
    //        Person person = personFactory.createPerson(name, address, mobile);
    //
    //        // Register the person
    //        boolean isRegistered = personService.registerPerson(person);
    //        if (isRegistered) {
    //            resp.getWriter().write("{\"message\": \"Person registered successfully!\"}");
    //        } else {
    //            resp.getWriter().write("{\"error\": \"Failed to register person.\"}");
    //        }
    //    }
    //
    //    private String readRequestBody(HttpServletRequest req) throws IOException {
    //        BufferedReader reader = req.getReader();
    //        StringBuilder requestBody = new StringBuilder();
    //        String line;
    //        while ((line = reader.readLine()) != null) {
    //            requestBody.append(line);
    //        }
    //        return requestBody.toString();
    //    }
    //}

    //this voilate sinle responsiblity principle so i crete person Registration Service and PersonFactoryService and JsonUtils
    // now every this handle duties separatly

    //package org.example.mega_city_cab_service_app.controller.Person;
    //
    //import jakarta.servlet.ServletException;
    //import jakarta.servlet.annotation.WebServlet;
    //import jakarta.servlet.http.HttpServlet;
    //import jakarta.servlet.http.HttpServletRequest;
    //import jakarta.servlet.http.HttpServletResponse;
    //import org.example.mega_city_cab_service_app.dao.PersonDAO;
    //
    //
    //import org.example.mega_city_cab_service_app.factory.managePersonFactory.AdminFactory;
    //import org.example.mega_city_cab_service_app.factory.managePersonFactory.EmployeeFactory;
    //import org.example.mega_city_cab_service_app.model.Person;
    //import org.example.mega_city_cab_service_app.service.PersonService;
    //import org.example.mega_city_cab_service_app.util.JsonUtils;
    //
    //import java.io.BufferedReader;
    //import java.io.IOException;
    //
    //@WebServlet(name = "PersonServlet", value = "/person")
    //public class PersonServlet extends HttpServlet {
    //    private final PersonService personService = new PersonService(new PersonDAO());
    //
    //    @Override
    //    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //        resp.setContentType("application/json");
    //
    //        String jsonInput = readRequestBody(req);
    //        String type = JsonUtils.extractValueFromJson(jsonInput, "type");
    //        String name = JsonUtils.extractValueFromJson(jsonInput, "name");
    //        String address = JsonUtils.extractValueFromJson(jsonInput, "address");
    //        String mobile = JsonUtils.extractValueFromJson(jsonInput, "mobile");
    //
    //        if (type == null || name == null || address == null || mobile == null) {
    //            resp.getWriter().write("{\"error\": \"Missing required fields in JSON input.\"}");
    //            return;
    //        }
    //
    //        Person person;
    //        if (type.equalsIgnoreCase("ADMIN")) {
    //            person = new AdminFactory().createPerson(name, address, mobile);
    //        } else if (type.equalsIgnoreCase("EMPLOYEE")) {
    //            double salary = Double.parseDouble(JsonUtils.extractValueFromJson(jsonInput, "salary"));
    //            int experience = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "experience"));
    //            person = new EmployeeFactory(salary, experience).createPerson(name, address, mobile);
    //        } else {
    //            resp.getWriter().write("{\"error\": \"Invalid person type.\"}");
    //            return;
    //        }
    //
    //        boolean isRegistered = personService.registerPerson(person);
    //        if (isRegistered) {
    //            resp.getWriter().write("{\"message\": \"Person registered successfully!\"}");
    //        } else {
    //            resp.getWriter().write("{\"error\": \"Failed to register person.\"}");
    //        }
    //    }
    //
    //    @Override
    //    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //        resp.setContentType("application/json");
    //
    //        String mobile = req.getParameter("mobile");
    //        if (mobile == null) {
    //            resp.getWriter().write("{\"error\": \"Mobile number is required.\"}");
    //            return;
    //        }
    //
    //        Person person = personService.getPerson(mobile);
    //        if (person != null) {
    //            resp.getWriter().write("{\"name\": \"" + person.getName() + "\", \"address\": \"" + person.getAddress() + "\", \"mobile\": \"" + person.getMobile() + "\"}");
    //        } else {
    //            resp.getWriter().write("{\"error\": \"Person not found.\"}");
    //        }
    //    }
    //
    //
    //    @Override
    //    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //        resp.setContentType("application/json");
    //
    //        // Extract mobile from URL parameter
    //        String originalMobile = req.getParameter("mobile");
    //        if (originalMobile == null || originalMobile.isEmpty()) {
    //            resp.getWriter().write("{\"error\": \"Mobile number is required in the URL parameter.\"}");
    //            return;
    //        }
    //
    //        // Read JSON input
    //        String jsonInput = readRequestBody(req);
    //        String type = JsonUtils.extractValueFromJson(jsonInput, "type");
    //        String name = JsonUtils.extractValueFromJson(jsonInput, "name");
    //        String address = JsonUtils.extractValueFromJson(jsonInput, "address");
    //        String newMobile = JsonUtils.extractValueFromJson(jsonInput, "mobile");
    //
    //        // Validate required fields
    //        if (type == null || name == null || address == null || newMobile == null) {
    //            resp.getWriter().write("{\"error\": \"Missing required fields in JSON input.\"}");
    //            return;
    //        }
    //
    //        // Create updated person object
    //        Person updatedPerson;
    //        if (type.equalsIgnoreCase("ADMIN")) {
    //            updatedPerson = new AdminFactory().createPerson(name, address, newMobile);
    //        } else if (type.equalsIgnoreCase("EMPLOYEE")) {
    //            double salary = Double.parseDouble(JsonUtils.extractValueFromJson(jsonInput, "salary"));
    //            int experience = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "experience"));
    //            updatedPerson = new EmployeeFactory(salary, experience).createPerson(name, address, newMobile);
    //        } else {
    //            resp.getWriter().write("{\"error\": \"Invalid person type.\"}");
    //            return;
    //        }
    //
    //        // Update the person
    //        boolean isUpdated = personService.updatePerson( originalMobile,updatedPerson);
    //        if (isUpdated) {
    //            resp.getWriter().write("{\"message\": \"Person updated successfully!\"}");
    //        } else {
    //            resp.getWriter().write("{\"error\": \"Failed to update person.\"}");
    //        }
    //    }
    //
    //    @Override
    //    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //        resp.setContentType("application/json");
    //
    //        String mobile = req.getParameter("mobile");
    //        if (mobile == null) {
    //            resp.getWriter().write("{\"error\": \"Mobile number is required.\"}");
    //            return;
    //        }
    //
    //        boolean isDeleted = personService.deletePerson(mobile);
    //        if (isDeleted) {
    //            resp.getWriter().write("{\"message\": \"Person deleted successfully!\"}");
    //        } else {
    //            resp.getWriter().write("{\"error\": \"Failed to delete person.\"}");
    //        }
    //    }
    //
    //    private String readRequestBody(HttpServletRequest req) throws IOException {
    //        BufferedReader reader = req.getReader();
    //        StringBuilder requestBody = new StringBuilder();
    //        String line;
    //        while ((line = reader.readLine()) != null) {
    //            requestBody.append(line);
    //        }
    //        return requestBody.toString();
    //    }
    //}

    package org.project.mega_city_cab_service_app.controller.Person;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.project.mega_city_cab_service_app.dao.PersonDAO;
    import org.project.mega_city_cab_service_app.service.PersonService.*;
    import org.project.mega_city_cab_service_app.service.security.AccessDeniedException;
    import org.project.mega_city_cab_service_app.service.security.AuthorizationService;
    import org.project.mega_city_cab_service_app.util.DBConnection;

    import java.io.IOException;

    @WebServlet(name = "PersonServlet", value = "/person")
    public class PersonServlet extends HttpServlet {

        private final PersonService personService = new PersonService(new PersonDAO(DBConnection.getInstance()));
        private final PersonFactoryService personFactoryService = new PersonFactoryService();
        private final PersonRegistrationService registrationService = new PersonRegistrationService(personService, personFactoryService);
        private final PersonUpdateService updateService = new PersonUpdateService(personService, personFactoryService);
        private final PersonRetrievalService retrievalService = new PersonRetrievalService(personService);
        private final PersonDeletionService deletionService = new PersonDeletionService(personService);

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                AuthorizationService.checkRole(req, "ADMIN", "EMPLOYEE");
                resp.setContentType("application/json");
                String jsonInput = readRequestBody(req);
                String response = registrationService.registerPerson(jsonInput);
                resp.getWriter().write(response);
            }
            catch (AccessDeniedException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                AuthorizationService.checkRole(req, "ADMIN");
                resp.setContentType("application/json");
                String mobile = req.getParameter("mobile");
                String response = retrievalService.getPerson(mobile);
                resp.getWriter().write(response);
            }
            catch (AccessDeniedException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }

        }

        @Override
        protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                AuthorizationService.checkRole(req, "ADMIN", "EMPLOYEE");
                resp.setContentType("application/json");
                String originalMobile = req.getParameter("mobile");
                String jsonInput = readRequestBody(req);
                String response = updateService.updatePerson(originalMobile, jsonInput);
                resp.getWriter().write(response);
            }
            catch (AccessDeniedException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            }

        }

        @Override
        protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try{
                AuthorizationService.checkRole(req, "ADMIN");
                resp.setContentType("application/json");
                String mobile = req.getParameter("mobile");
                String response = deletionService.deletePerson(mobile);
                resp.getWriter().write(response);
            }
            catch (AccessDeniedException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
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