//
//package org.example.mega_city_cab_service_app.controller;
//
//import org.example.mega_city_cab_service_app.dao.PersonDAO;
//import org.example.mega_city_cab_service_app.factory.AdminFactory;
//import org.example.mega_city_cab_service_app.factory.EmployeeFactory;
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
// now every this handle there duties separatly

package org.example.mega_city_cab_service_app.controller;

import org.example.mega_city_cab_service_app.dao.PersonDAO;
import org.example.mega_city_cab_service_app.factory.AdminFactory;
import org.example.mega_city_cab_service_app.factory.FactoryRegistry;
import org.example.mega_city_cab_service_app.service.PersonFactoryService;
import org.example.mega_city_cab_service_app.service.PersonRegistrationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mega_city_cab_service_app.service.PersonService;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "PersonServlet", value = "/registerPerson")
public class PersonServlet extends HttpServlet {
    private PersonRegistrationService registrationService;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize services (manually or via dependency injection)
        this.registrationService = new PersonRegistrationService(
                new PersonService(new PersonDAO()),
                new PersonFactoryService()
        );

        // Register factories during initialization
        FactoryRegistry.registerFactory("ADMIN", new AdminFactory());
        FactoryRegistry.registerFactory("EMPLOYEE", (name, address, mobile) -> {
            // This lambda is a placeholder; actual parameters will be handled in doPost
            return null;
        });
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        // Read JSON input
        String jsonInput = readRequestBody(req);

        // Delegate registration to the service
        String response = registrationService.registerPerson(jsonInput);
        resp.getWriter().write(response);
    }

    private String readRequestBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return requestBody.toString();
    }
}
