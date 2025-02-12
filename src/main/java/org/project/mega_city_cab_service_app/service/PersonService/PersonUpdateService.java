package org.project.mega_city_cab_service_app.service;

import org.project.mega_city_cab_service_app.factory.PersonFactory;
import org.project.mega_city_cab_service_app.model.Person;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class PersonUpdateService {
    private final PersonService personService;
    private final PersonFactoryService personFactoryService;

    public PersonUpdateService(PersonService personService, PersonFactoryService personFactoryService) {
        this.personService = personService;
        this.personFactoryService = personFactoryService;
    }

    public String updatePerson(String originalMobile, String jsonInput) {
        String type = JsonUtils.extractValueFromJson(jsonInput, "type");
        String name = JsonUtils.extractValueFromJson(jsonInput, "name");
        String address = JsonUtils.extractValueFromJson(jsonInput, "address");
        String newMobile = JsonUtils.extractValueFromJson(jsonInput, "mobile");

        if (type == null || name == null || address == null || newMobile == null) {
            return "{\"error\": \"Missing required fields in JSON input.\"}";
        }

        // Get the factory from the factory service
        PersonFactory factory = personFactoryService.getFactory(type);
        if (factory == null) {
            return "{\"error\": \"Invalid person type.\"}";
        }

        // Create the updated person object
        Person updatedPerson = factory.createPerson(jsonInput,name, address, newMobile);

        // Update the person
        boolean isUpdated = personService.updatePerson(originalMobile, updatedPerson);
        if (isUpdated) {
            return "{\"message\": \"Person updated successfully!\"}";
        } else {
            return "{\"error\": \"Failed to update person.\"}";
        }
    }
}