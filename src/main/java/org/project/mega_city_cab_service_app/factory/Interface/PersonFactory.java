package org.project.mega_city_cab_service_app.factory.Interface;
import org.project.mega_city_cab_service_app.model.Parent.Person;

//public interface PersonFactory {
//    Person createPerson(String name, String address, String mobile);
//}

public interface PersonFactory {
    Person createPerson(String jsonInput, String name, String address, String mobile, String  username ,String password);
}