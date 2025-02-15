package org.project.mega_city_cab_service_app.service.PersonService;
import org.project.mega_city_cab_service_app.dao.PersonDAO;
import org.project.mega_city_cab_service_app.model.Parent.Person;

//focus on business logic and orchestration
public class PersonService {
    private final PersonDAO personDAO;
//Constructor Parameters=>PersonDAO object as a parameter
    //loose coupling
    public PersonService(PersonDAO personDAO) {

        this.personDAO = personDAO;
    }
    //Internally this is how its work
        //Person person = new Customer("John Doe", "123 Main St", "9876543210");
        //personService.registerPerson(person);
    public boolean registerPerson(Person person) {

        return personDAO.savePerson(person);
    }

    public Person getPerson(String mobile) {

        return personDAO.findPersonByMobile(mobile);
    }
    //violate SRP
//    public boolean updatePerson(String originalMobile, Person updatedPerson) {
//        // Fetch the existing person
//        Person existingPerson = personDAO.findPersonByMobile(originalMobile);
//        if (existingPerson == null) {
//            return false; // Person not found
//        }
//
//        // If the mobile number is being updated, delete the old entry
//        if (!originalMobile.equals(updatedPerson.getMobile())) {
//            personDAO.deletePerson(originalMobile);
//        }
//
//        // Save the updated person
//        return personDAO.savePerson(updatedPerson);
//    }
    public boolean updatePerson(String originalMobile, Person updatedPerson) {

        return personDAO.updatePerson(originalMobile, updatedPerson);
    }
    public boolean deletePerson(String mobile) {

        return personDAO.deletePerson(mobile);
    }

}
