package org.project.mega_city_cab_service_app.service.PersonService;

import org.project.mega_city_cab_service_app.model.Parent.Person;

public class PersonRetrievalService {
    private final PersonService personService;

    public PersonRetrievalService(PersonService personService) {
        this.personService = personService;
    }

    public String getPerson(String mobile) {
        Person person = personService.getPerson(mobile);
        if (person != null) {
            return "{\"name\": \"" + person.getName() + "\", \"address\": \"" + person.getAddress() + "\", \"mobile\": \"" + person.getMobile() + "\"}";
        } else {
            return "{\"error\": \"Person not found.\"}";
        }
    }
}