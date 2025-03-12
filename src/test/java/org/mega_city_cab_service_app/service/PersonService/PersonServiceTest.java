package org.mega_city_cab_service_app.service.PersonService;

import org.junit.Before;
import org.junit.Test;
import org.mega_city_cab_service_app.service.PersonService.StubPersonRepository;
import org.project.mega_city_cab_service_app.dao.PersonDAO;
import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.model.person.Admin;
import org.project.mega_city_cab_service_app.model.person.Customer;
import org.project.mega_city_cab_service_app.repository.Person.PersonRepository;
import org.project.mega_city_cab_service_app.service.PersonService.PersonService;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PersonServiceTest {

    private PersonService personService;
    private StubPersonRepository stubPersonRepository;

    @Before
    public void setUp() {
        // Initialize the stub repository
        stubPersonRepository = new StubPersonRepository();

        // Create a map of repositories (only one type for simplicity)
        Map<String, PersonRepository> repositories = new HashMap<>();
        repositories.put("ADMIN", stubPersonRepository);
        repositories.put("CUSTOMER", stubPersonRepository);

        // Inject the stub repository into the DAO
        PersonDAO personDAO = new PersonDAO(repositories); // Use the correct constructor

        // Inject the DAO into the service
        personService = new PersonService(personDAO);
    }

    @Test
    public void testRegisterPerson_Success() {
        // Arrange
        Person admin = new Admin("John Doe", "123 Main St", "9876543210", "admin", "password");

        // Act
        boolean result = personService.registerPerson(admin);

        // Assert
        assertTrue(result);
        assertNotNull(stubPersonRepository.findByMobile("9876543210"));
    }

    @Test
    public void testRegisterPerson_DuplicateMobile() {
        // Arrange
        Person admin1 = new Admin("John Doe", "123 Main St", "9876543210", "admin", "password");
        Person admin2 = new Admin("Jane Doe", "456 Elm St", "9876543210", "admin", "password");

        // Act
        boolean result1 = personService.registerPerson(admin1);
        boolean result2 = personService.registerPerson(admin2);

        // Assert
        assertTrue(result1); // First registration should succeed
        assertFalse(result2); // Second registration should fail due to duplicate mobile
    }

    @Test
    public void testGetAllPersonsByType_Success() {
        // Arrange
        Person admin1 = new Admin("John Doe", "123 Main St", "9876543210", "admin", "password");
        Person admin2 = new Admin("Jane Doe", "456 Elm St", "9876543211", "admin", "password");
        Person customer = new Customer("Alice Brown", "789 Oak St", "9876543212", "customer", "password", 5, "Loyal customer");

        stubPersonRepository.save(admin1);
        stubPersonRepository.save(admin2);
        stubPersonRepository.save(customer);

        // Act
        List<Person> admins = personService.getAllPersonsByType("ADMIN");
        List<Person> customers = personService.getAllPersonsByType("CUSTOMER");

        // Assert
        assertEquals(2, admins.size());
        assertEquals(1, customers.size());

        assertEquals("John Doe", admins.get(0).getName());
        assertEquals("Jane Doe", admins.get(1).getName());
        assertEquals("Alice Brown", customers.get(0).getName());
    }

    @Test
    public void testGetAllPersonsByType_NoResults() {
        // Act
        List<Person> drivers = personService.getAllPersonsByType("DRIVER");

        // Assert
        assertTrue(drivers.isEmpty());
    }

    @Test
    public void testUpdatePerson_Success() {
        // Arrange
        Person admin = new Admin("John Doe", "123 Main St", "9876543210", "admin", "password");
        stubPersonRepository.save(admin);

        Person updatedAdmin = new Admin("John Updated", "Updated Address", "9876543210", "admin", "newpassword");

        // Act
        boolean result = personService.updatePerson("9876543210", updatedAdmin);

        // Assert
        assertTrue(result);
        Person updatedPerson = stubPersonRepository.findByMobile("9876543210");
        assertNotNull(updatedPerson);
        assertEquals("John Updated", updatedPerson.getName());
        assertEquals("Updated Address", updatedPerson.getAddress());
    }

    @Test
    public void testDeletePerson_Success() {
        // Arrange
        Person admin = new Admin("John Doe", "123 Main St", "9876543210", "admin", "password");
        stubPersonRepository.save(admin);

        // Act
        boolean result = personService.deletePerson("9876543210");

        // Assert
        assertTrue(result);
        assertNull(stubPersonRepository.findByMobile("9876543210"));
    }

    @Test
    public void testDeletePerson_NotFound() {
        // Act
        boolean result = personService.deletePerson("nonexistent-mobile");

        // Assert
        assertFalse(result);
    }
}