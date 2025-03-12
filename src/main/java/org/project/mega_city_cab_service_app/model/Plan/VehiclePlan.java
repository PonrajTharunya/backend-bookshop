package org.project.mega_city_cab_service_app.model.Plan;

public class VehiclePlan {
    private int id; // Primary key (auto-generated)
    private String planName;

    public VehiclePlan(String planName) {
        this.planName = planName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}