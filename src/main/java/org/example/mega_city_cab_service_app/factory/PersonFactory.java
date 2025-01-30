package org.example.mega_city_cab_service_app.factory;

import org.example.mega_city_cab_service_app.model.Person;

public interface PersonFactory {
    Person createPerson(String name, String address, String mobile);
}