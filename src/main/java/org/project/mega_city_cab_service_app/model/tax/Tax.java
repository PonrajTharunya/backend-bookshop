package org.project.mega_city_cab_service_app.model.tax;

public class Tax {
    private int taxId; // Primary key (auto-generated)
    private String description;
    private double taxRate;
    private boolean status;

    public Tax(String description, double taxRate, boolean status) {
        this.description = description;
        this.taxRate = taxRate;
        this.status = status;
    }

    // Getters and Setters
    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}