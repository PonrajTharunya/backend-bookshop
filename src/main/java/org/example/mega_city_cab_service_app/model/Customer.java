package org.example.mega_city_cab_service_app.model;
public class Customer extends Person {
    private final int rating;
    private final String description;
    public Customer( String name,String address,String mobile, int rating,String description ) {
        super(name,address,mobile);
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
