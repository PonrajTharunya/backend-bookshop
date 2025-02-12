package org.project.mega_city_cab_service_app.model.Parent;

public abstract class Person {
    private final String name;
    private final String address;
    private final String mobile;
    private final String username;
    private final String password;



    protected Person(String name, String address, String mobile, String username, String password ) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.username = username;
        this.password = password;
    }

    public abstract String getType();

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getMobile() { return mobile; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

}