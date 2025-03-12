package org.project.mega_city_cab_service_app.repository.Person;

import org.project.mega_city_cab_service_app.model.Parent.Person;

import java.util.List;

public interface PersonRepository {
    boolean save(Person person);
    Person findByMobile(String mobile);
    boolean update(String originalMobile, Person updatedPerson);
    boolean delete(String mobile);
    Person findById(int id);

    // New method to retrieve all persons of a specific type
    List<Person> findAll();
}