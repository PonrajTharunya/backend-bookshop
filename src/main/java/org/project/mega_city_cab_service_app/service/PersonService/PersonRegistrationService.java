//package org.example.mega_city_cab_service_app.service;
//
//
//import org.example.mega_city_cab_service_app.factory.PersonFactory;
//import org.example.mega_city_cab_service_app.model.Person;
//import org.example.mega_city_cab_service_app.util.JsonUtils;
//
//public class PersonRegistrationService {
//    private final PersonService personService;
//    private final PersonFactoryService personFactoryService;
//
//    public PersonRegistrationService(PersonService personService, PersonFactoryService personFactoryService) {
//        this.personService = personService;
//        this.personFactoryService = personFactoryService;
//    }
//
//    public String registerPerson(String jsonInput) {
//        String type = JsonUtils.extractValueFromJson(jsonInput, "type");
//        String name = JsonUtils.extractValueFromJson(jsonInput, "name");
//        String address = JsonUtils.extractValueFromJson(jsonInput, "address");
//        String mobile = JsonUtils.extractValueFromJson(jsonInput, "mobile");
//
//
//        if (type == null || name == null || address == null || mobile == null) {
//            return "{\"error\": \"Missing required fields in JSON input.\"}";
//        }
//
//        // Get the factory from the factory service
//        PersonFactory factory = personFactoryService.getFactory(type, jsonInput);
//        if (factory == null) {
//            return "{\"error\": \"Invalid person type.\"}";
//        }
//
//        // Create the person object
//        Person person = factory.createPerson(name, address, mobile);
//
//        // Register the person
//        boolean isRegistered = personService.registerPerson(person);
//        if (isRegistered) {
//            return "{\"message\": \"Person registered successfully!\"}";
//        } else {
//            return "{\"error\": \"Failed to register person.\"}";
//        }
//    }
//}


package org.project.mega_city_cab_service_app.service;

import org.project.mega_city_cab_service_app.factory.PersonFactory;
import org.project.mega_city_cab_service_app.model.Person;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class PersonRegistrationService {
    private final PersonService personService;
    private final PersonFactoryService personFactoryService;

    public PersonRegistrationService(PersonService personService, PersonFactoryService personFactoryService) {
        this.personService = personService;
        this.personFactoryService = personFactoryService;
    }

    public String registerPerson(String jsonInput) {
        String type = JsonUtils.extractValueFromJson(jsonInput, "type");
        String name = JsonUtils.extractValueFromJson(jsonInput, "name");
        String address = JsonUtils.extractValueFromJson(jsonInput, "address");
        String mobile = JsonUtils.extractValueFromJson(jsonInput, "mobile");

        if (type == null || name == null || address == null || mobile == null) {
            return "{\"error\": \"Missing required fields in JSON input.\"}";
        }

        // Get the factory from the factory service
        PersonFactory factory = personFactoryService.getFactory(type);
        if (factory == null) {
            return "{\"error\": \"Invalid person type.\"}";
        }

        // Create the person object
        Person person = factory.createPerson(jsonInput,name, address, mobile);

        // Register the person
        boolean isRegistered = personService.registerPerson(person);
        if (isRegistered) {
            return "{\"message\": \"Person registered successfully!\"}";
        } else {
            return "{\"error\": \"Failed to register person.\"}";
        }
    }
}