package org.project.mega_city_cab_service_app.model.person;

public class Driver extends Employee {
    private String licenseNumber;
    private String licenseType;

    public Driver(String name, String address, String mobile, String username,String password, double salary, int experience, String licenseNumber, String licenseType) {
        super(name, address, mobile,username,password, salary, experience);
        this.licenseNumber = licenseNumber;
        this.licenseType = licenseType;
    }
    @Override
    public String getType(){
            return "DRIVER";
    }
    public String getLicenseNumber(){
        return licenseNumber;
    }
    public String getLicenseType(){
        return licenseType;
    }

}

