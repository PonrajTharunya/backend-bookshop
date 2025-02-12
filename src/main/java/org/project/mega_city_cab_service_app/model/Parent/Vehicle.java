package org.project.mega_city_cab_service_app.model.Parent;

public abstract class Vehicle {
    private final String name;
    private final String model;
    private final String color;
    private final int year;
    private final int registrationNumber;
    private final int seatingCapacity; // Common attribute

    protected Vehicle(String name, String model, String color, int year, int registrationNumber, int seatingCapacity) {
        this.name = name;
        this.model = model;
        this.color = color;
        this.year = year;
        this.registrationNumber = registrationNumber;
        this.seatingCapacity = seatingCapacity;
    }

    public abstract String getType(); // Abstract method to define vehicle type

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getYear() {
        return year;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }
}