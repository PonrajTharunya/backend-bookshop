package org.project.mega_city_cab_service_app.factory.managePersonFactory;

import org.project.mega_city_cab_service_app.factory.Interface.PersonFactory;
import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.model.person.Customer;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class CustomerFactory implements PersonFactory {
//    private final int rating;
//    private final String description;
//
//    public CustomerFactory(int rating, String description) {
//        this.rating = rating;
//        this.description = description;
//    }

//    @Override
//    public Person createPerson(String name, String address, String mobile){
//        return new Customer(name,address,mobile,rating,description);
//    }
    @Override
    public Person createPerson(String jsonInput, String name, String address, String mobile, String  username ,String password) {
        int rating = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "rating"));
        String description = JsonUtils.extractValueFromJson(jsonInput, "description");
        return new Customer(name, address, mobile, username ,password, rating, description);
    }

}
