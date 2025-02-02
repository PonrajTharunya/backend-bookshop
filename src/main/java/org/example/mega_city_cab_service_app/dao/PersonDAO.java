package org.example.mega_city_cab_service_app.dao;

import org.example.mega_city_cab_service_app.model.Person;
import org.example.mega_city_cab_service_app.model.person.Employee;

import java.util.HashMap;
import java.util.Map;


public class PersonDAO {
    private final Map<String, Person> personMap = new HashMap<>();

    public boolean savePerson(Person person) {
        System.out.println("Saving Person Type: " + person.getType());
        System.out.println("Name: " + person.getName());
        System.out.println("Address: " + person.getAddress());
        System.out.println("Mobile: " + person.getMobile());

        if (person instanceof Employee) {
            Employee emp = (Employee) person;
            System.out.println("Salary: " + emp.getSalary());
            System.out.println("Experience: " + emp.getExperience());
        }

        personMap.put(person.getMobile(), person);
        return true;
    }
    public Person findPersonByMobile(String mobile) {
        return personMap.get(mobile);
    }
    public boolean updatePerson(Person person) {
        if (personMap.containsKey(person.getMobile())) {
            personMap.put(person.getMobile(), person);
            System.out.println("Updated Person: " + person.getName());
            return true;
        }
        System.out.println("Person not found for update.");
        return false;
    }
    public boolean deletePerson(String mobile) {
        Person person = personMap.remove(mobile);
        if (person != null) {
            System.out.println("Deleted Person: " + person.getName());
            return true;
        }
        System.out.println("Person not found for deletion.");
        return false;
    }

}