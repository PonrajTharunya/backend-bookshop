package org.example.mega_city_cab_service_app.model.person;

import org.example.mega_city_cab_service_app.model.Person;

public class Employee extends Person {
    private final double salary;
    private final int experience;

    public Employee(String name, String address, String mobile, double salary, int experience) {
        super(name, address, mobile);
        this.salary = salary;
        this.experience = experience;
    }

    @Override
    public String getType() {
        return "EMPLOYEE";
    }
    public double getSalary() { return salary; }
    public int getExperience() { return experience; }
}