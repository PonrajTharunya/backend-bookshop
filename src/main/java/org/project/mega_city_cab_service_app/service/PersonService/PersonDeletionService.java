package org.project.mega_city_cab_service_app.service.PersonService;

public class PersonDeletionService {
    private final PersonService personService;

    public PersonDeletionService(PersonService personService) {
        this.personService = personService;
    }

    public String deletePerson(String mobile) {
        boolean isDeleted = personService.deletePerson(mobile);
        if (isDeleted) {
            return "{\"message\": \"Person deleted successfully!\"}";
        } else {
            return "{\"error\": \"Failed to delete person.\"}";
        }
    }
}