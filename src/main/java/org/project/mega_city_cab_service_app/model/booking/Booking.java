package org.project.mega_city_cab_service_app.model.booking;

import java.time.LocalDateTime;

public class Booking {
    private int orderNo; // Primary key (auto-generated)
    private int customerId; // Foreign key
    private String pickupLocation;
    private String dropLocation;
    private String bookingType; // "with_driver" or "without_driver"
    private int vehicleId; // Foreign key
    private Integer driverId; // Optional foreign key (null if "without_driver")
    private int initialKm;
    private int finalKm;
    private LocalDateTime pickupTime;
    private LocalDateTime returnTime;
    private int daysNeeded;

    // Constructor
    public Booking(int customerId, String pickupLocation, String dropLocation, String bookingType, int vehicleId,
                   Integer driverId, int initialKm, int finalKm, LocalDateTime pickupTime, LocalDateTime returnTime, int daysNeeded) {
        this.customerId = customerId;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.bookingType = bookingType;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.initialKm = initialKm;
        this.finalKm = finalKm;
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
        this.daysNeeded = daysNeeded;
    }

    // Getters and Setters
    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public String getBookingType() {
        return bookingType;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public int getInitialKm() {
        return initialKm;
    }

    public int getFinalKm() {
        return finalKm;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public int getDaysNeeded() {
        return daysNeeded;
    }
}