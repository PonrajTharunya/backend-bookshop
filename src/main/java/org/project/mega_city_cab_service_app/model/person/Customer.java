package org.project.mega_city_cab_service_app.model.person;

import org.project.mega_city_cab_service_app.model.Parent.Person;

public class Customer extends Person {
    private final int rating;
    private final String description;
    public Customer( String name,String address,String mobile,String  username , String password,int rating,String description ) {
        super(name,address,mobile, username ,password);
        this.rating = rating;
        this.description = description;
    }
    @Override
    public String getType(){
        return "CUSTOMER";
    }
    public int getRating() {return rating;}
    public String getDescription() {return description;}
}
