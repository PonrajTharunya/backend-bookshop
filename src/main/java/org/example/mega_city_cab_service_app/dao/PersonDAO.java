package org.example.mega_city_cab_service_app.dao;

import org.example.mega_city_cab_service_app.model.Person;
import org.example.mega_city_cab_service_app.model.Employee;

public class PersonDAO {
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

        return true; // Simulate DB insert
    }
}