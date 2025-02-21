package org.project.mega_city_cab_service_app.model.Range;

public class DistanceRange {
    private long id; // Primary key (auto-generated)
    private int minDistance;
    private int maxDistance;

    public DistanceRange() {}

    public DistanceRange(int minDistance, int maxDistance) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
}