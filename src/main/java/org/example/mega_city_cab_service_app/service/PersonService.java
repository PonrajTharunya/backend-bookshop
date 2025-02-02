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

    public Person getPerson(String mobile) {
        return personDAO.findPersonByMobile(mobile);
    }
    public boolean updatePerson(String originalMobile, Person updatedPerson) {
        // Fetch the existing person
        Person existingPerson = personDAO.findPersonByMobile(originalMobile);
        if (existingPerson == null) {
            return false; // Person not found
        }

        // If the mobile number is being updated, delete the old entry
        if (!originalMobile.equals(updatedPerson.getMobile())) {
            personDAO.deletePerson(originalMobile);
        }

        // Save the updated person
        return personDAO.savePerson(updatedPerson);
    }

    public boolean deletePerson(String mobile) {
        return personDAO.deletePerson(mobile);
    }


}
