package org.project.mega_city_cab_service_app.model.Parent;

public abstract class Person {
    private final String name;
    private final String address;
    private final String mobile;
    private final String username;
    private final String password;
    private  int id; // Add this field


    protected Person(String name, String address, String mobile, String username, String password) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.id=-1;

    }

    public abstract String getType();

    // Getter and Setter for ID


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getMobile() { return mobile; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

}