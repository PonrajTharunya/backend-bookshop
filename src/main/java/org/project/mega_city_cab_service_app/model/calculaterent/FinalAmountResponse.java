package org.project.mega_city_cab_service_app.model.calculaterent;

public class FinalAmountResponse {
    private final int orderId;
    private final double finalAmount;
    private final String distanceUsed;
    private final String basePrice;
    private final String extraKilometers;
    private final String totalCostBeforeDiscount;
    private final String discount;
    private final String totalCostAfterDiscount;
    private final String tax;
    private final String calculationBreakdown;
    private final String driverFee; // New field for driver fee

    public FinalAmountResponse(int orderId, double finalAmount, String distanceUsed, String basePrice,
                               String extraKilometers, String totalCostBeforeDiscount, String discount,
                               String totalCostAfterDiscount, String tax, String calculationBreakdown, String driverFee) {
        this.orderId = orderId;
        this.finalAmount = finalAmount;
        this.distanceUsed = distanceUsed;
        this.basePrice = basePrice;
        this.extraKilometers = extraKilometers;
        this.totalCostBeforeDiscount = totalCostBeforeDiscount;
        this.discount = discount;
        this.totalCostAfterDiscount = totalCostAfterDiscount;
        this.tax = tax;
        this.calculationBreakdown = calculationBreakdown;
        this.driverFee = driverFee;
    }

    // Getters
    public int getOrderId() { return orderId; }
    public double getFinalAmount() { return finalAmount; }
    public String getDistanceUsed() { return distanceUsed; }
    public String getBasePrice() { return basePrice; }
    public String getExtraKilometers() { return extraKilometers; }
    public String getTotalCostBeforeDiscount() { return totalCostBeforeDiscount; }
    public String getDiscount() { return discount; }
    public String getTotalCostAfterDiscount() { return totalCostAfterDiscount; }
    public String getTax() { return tax; }
    public String getCalculationBreakdown() { return calculationBreakdown; }
    public String getDriverFee() { return driverFee; }
}