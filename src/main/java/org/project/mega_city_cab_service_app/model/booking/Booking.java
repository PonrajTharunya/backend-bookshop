package org.project.mega_city_cab_service_app.model.booking;

import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.model.PlanPrice.PlanPrice;
import org.project.mega_city_cab_service_app.model.Range.DistanceRange;
import org.project.mega_city_cab_service_app.model.VehiclePlan;
import org.project.mega_city_cab_service_app.model.person.Customer;
import org.project.mega_city_cab_service_app.model.person.Driver;
import org.project.mega_city_cab_service_app.model.person.Employee;
import org.project.mega_city_cab_service_app.model.tax.Tax;
import org.project.mega_city_cab_service_app.model.vehical.PremiumVehicle;
import org.project.mega_city_cab_service_app.model.vehical.VIPVehicle;

import java.time.LocalDateTime;

public class Booking {
    private int orderNo; // Primary key (auto-generated)
    private int customerId;

    private String pickupLocation;
    private String dropLocation;
    private String bookingType; // "with_driver" or "without_driver"
    private int vehicleId;
   // Foreign key
    private Integer driverId;

    private int initialKm;
    private int finalKm;
    private LocalDateTime pickupTime;
    private LocalDateTime returnTime;
    private int daysNeeded;
    private int Tax_id;
    private Tax tax;  // Associated Tax object from tax
    private int employee_id;
    private int package_id;


    //  have to put in to separate file cz lecture said max parameter 8 can pass in each function :)
    private Customer customer;
    private Driver driver;
    private Employee employee;

    private Vehicle vehicle;
    private VIPVehicle vipVehicle;
    private PremiumVehicle premiumVehicle;

    private VehiclePlan vehiclePlan;
    private DistanceRange distanceRange;
    private PlanPrice planPrice;



    // Constructor
    public Booking(int customerId, String pickupLocation, String dropLocation, String bookingType, int vehicleId,
                   Integer driverId, int initialKm, int finalKm, LocalDateTime pickupTime, LocalDateTime returnTime, int daysNeeded,int tax_id,int employee_id, int package_id ) {
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
        this.Tax_id = tax_id;
        this.employee_id = employee_id;
        this.package_id = package_id;
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
    public int getTax_id() {
        return Tax_id;
    }
    public int getEmployee_id() {
        return employee_id;
    }
    public int getPackage_id() {
        return package_id;
    }

    public Tax getTax() {
        return tax;
    }
    public void setTax(Tax tax) {
        this.tax = tax;
    }


    // Getters and Setters for new fields
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Getters and Setters for new fields
    public VehiclePlan getVehiclePlan() {
        return vehiclePlan;
    }

    public void setVehiclePlan(VehiclePlan vehiclePlan) {
        this.vehiclePlan = vehiclePlan;
    }

    public DistanceRange getDistanceRange() {
        return distanceRange;
    }

    public void setDistanceRange(DistanceRange distanceRange) {
        this.distanceRange = distanceRange;
    }

    public PlanPrice getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(PlanPrice planPrice) {
        this.planPrice = planPrice;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setVIPVehicle(VIPVehicle vipVehicle) {
        this.vipVehicle = vipVehicle;
    }

    public void setPremiumVehicle(PremiumVehicle premiumVehicle) {
        this.premiumVehicle = premiumVehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VIPVehicle getVipVehicle() {
        return vipVehicle;
    }

    public PremiumVehicle getPremiumVehicle() {
        return premiumVehicle;
    }
}
