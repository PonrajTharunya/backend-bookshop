package org.mega_city_cab_service_app.service.PersonService;

import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.repository.Person.PersonRepository;

import java.util.*;
import java.util.stream.Collectors;

class StubPersonRepository implements PersonRepository {
    private final Map<String, Person> personMap = new HashMap<>();
    private int nextId = 1;

    @Override
    public boolean save(Person person) {
        if (personMap.containsKey(person.getMobile())) {
            return false; // Duplicate mobile number
        }
        person.setId(nextId++);
        personMap.put(person.getMobile(), person);
        return true;
    }

    @Override
    public Person findByMobile(String mobile) {
        return personMap.get(mobile);
    }

    @Override
    public boolean update(String originalMobile, Person updatedPerson) {
        if (!personMap.containsKey(originalMobile)) {
            return false; // Person not found
        }
        personMap.remove(originalMobile);
        personMap.put(updatedPerson.getMobile(), updatedPerson);
        return true;
    }

    @Override
    public boolean delete(String mobile) {
        return personMap.remove(mobile) != null;
    }

    @Override
    public Person findById(int id) {
        return personMap.values().stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Person> findAll() {
        return List.copyOf(personMap.values());
    }
}