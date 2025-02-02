package org.example.mega_city_cab_service_app.factory.managePersonFactory;

import org.example.mega_city_cab_service_app.factory.PersonFactory;
import org.example.mega_city_cab_service_app.model.Person;
import org.example.mega_city_cab_service_app.model.person.Customer;

public class CustomerFactory implements PersonFactory {
    private final int rating;
    private final String description;

    public CustomerFactory(int rating, String description) {
        this.rating = rating;
        this.description = description;
    }

    @Override
    public Person createPerson(String name, String address, String mobile){
        return new Customer(name,address,mobile,rating,description);
    }

}
