package org.project.mega_city_cab_service_app.model.PlanPrice;

public class PlanPrice {
    private long id; // Primary key (auto-generated)
    private long distanceRangeId; // Foreign key referencing distance_ranges(id)
    private double price;
    private double extraKmPrice;
    private double discount;
    private int vehiclePlanId; // Foreign key referencing vehicle_plan(id)

    public PlanPrice() {}

    public PlanPrice(long distanceRangeId, double price, double extraKmPrice, double discount, int vehiclePlanId) {
        this.distanceRangeId = distanceRangeId;
        this.price = price;
        this.extraKmPrice = extraKmPrice;
        this.discount = discount;
        this.vehiclePlanId = vehiclePlanId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDistanceRangeId() {
        return distanceRangeId;
    }

    public void setDistanceRangeId(long distanceRangeId) {
        this.distanceRangeId = distanceRangeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getExtraKmPrice() {
        return extraKmPrice;
    }

    public void setExtraKmPrice(double extraKmPrice) {
        this.extraKmPrice = extraKmPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getVehiclePlanId() {
        return vehiclePlanId;
    }

    public void setVehiclePlanId(int vehiclePlanId) {
        this.vehiclePlanId = vehiclePlanId;
    }
}