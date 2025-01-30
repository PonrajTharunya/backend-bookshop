package org.example.mega_city_cab_service_app.service;

import org.example.mega_city_cab_service_app.dao.PersonDAO;
import org.example.mega_city_cab_service_app.model.Person;

public class PersonService {
    private final PersonDAO personDAO;

    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public boolean registerPerson(Person person) {
        return personDAO.savePerson(person);
    }
}
