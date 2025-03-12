package org.project.mega_city_cab_service_app.service.PersonService;

import org.project.mega_city_cab_service_app.model.person.*;
import org.project.mega_city_cab_service_app.model.Parent.Person;

import java.util.List;

public class PersonRetrievalService {
    private final PersonService personService;

    public PersonRetrievalService(PersonService personService) {
        this.personService = personService;
    }
    /**
     * Retrieves a person by their mobile number and returns a JSON representation.
     */
    public String getPerson(String mobile) {
        Person person = personService.getPerson(mobile);
        if (person != null) {
            return buildPersonJson(person);
        } else {
            return "{\"error\": \"Person not found.\"}";
        }
    }

    /**
     * Retrieves a person by their ID and returns a JSON representation.
     */
    public String getPersonById(int id) {
        Person person = personService.getPersonById(id);
        if (person != null) {
            return buildPersonJson(person);
        } else {
            return "{\"error\": \"Person not found.\"}";
        }
    }
    public String getAllPersonsByType(String type) {
        List<Person> persons = personService.getAllPersonsByType(type);
        if (persons.isEmpty()) {
            return "{\"error\": \"No persons found for type: " + type + "\"}";
        }

        StringBuilder jsonBuilder = new StringBuilder("[");
        for (Person person : persons) {
            jsonBuilder.append(buildPersonJson(person)).append(",");
        }

        // Remove the trailing comma and close the JSON array
        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("]");

        return jsonBuilder.toString();
    }
    /**
     * Builds a JSON string based on the type of the person.
     */
    private String buildPersonJson(Person person) {
        StringBuilder jsonBuilder = new StringBuilder();

        // Common fields for all types
        jsonBuilder.append("{\"id\": ").append(person.getId()) // Use the getId() method
                .append(", \"name\": \"").append(person.getName()).append("\"")
                .append(", \"address\": \"").append(person.getAddress()).append("\"")
                .append(", \"mobile\": \"").append(person.getMobile()).append("\"")
                .append(", \"type\": \"").append(person.getType()).append("\"");

        // Add type-specific fields
        switch (person.getType()) {
            case "CUSTOMER":
                Customer customer = (Customer) person;
                jsonBuilder.append(", \"rating\": ").append(customer.getRating())
                        .append(", \"description\": \"").append(customer.getDescription()).append("\"");
                break;

            case "EMPLOYEE":
                Employee employee = (Employee) person;
                jsonBuilder.append(", \"salary\": ").append(employee.getSalary())
                        .append(", \"experience\": ").append(employee.getExperience());
                break;

            case "DRIVER":
                Driver driver = (Driver) person;
                jsonBuilder.append(", \"salary\": ").append(driver.getSalary())
                        .append(", \"experience\": ").append(driver.getExperience())
                        .append(", \"licenseNumber\": \"").append(driver.getLicenseNumber()).append("\"")
                        .append(", \"licenseType\": \"").append(driver.getLicenseType()).append("\"");
                break;

            case "ADMIN":
                // No additional fields for Admin, but you can add them if needed
                break;

            default:
                throw new IllegalArgumentException("Unsupported person type: " + person.getType());
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}