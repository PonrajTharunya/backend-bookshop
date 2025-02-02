package org.example.mega_city_cab_service_app.model;

public abstract class Person {
    private final String name;
    private final String address;
    private final String mobile;



    protected Person(String name, String address, String mobile ) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;

    }

    public abstract String getType();

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getMobile() { return mobile; }

}