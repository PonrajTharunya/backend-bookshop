package org.project.mega_city_cab_service_app.service.distanceRange;

import org.project.mega_city_cab_service_app.dao.DistanceRange.DistanceRangeDAO;
import org.project.mega_city_cab_service_app.model.Range.DistanceRange;


import java.util.List;

public class DistanceRangeService {
    private final DistanceRangeDAO distanceRangeDAO;

    public DistanceRangeService(DistanceRangeDAO distanceRangeDAO) {
        this.distanceRangeDAO = distanceRangeDAO;
    }

    public boolean addDistanceRange(DistanceRange distanceRange) {
        return distanceRangeDAO.addDistanceRange(distanceRange);
    }

    public List<DistanceRange> getAllDistanceRanges() {
        return distanceRangeDAO.getAllDistanceRanges();
    }

    public DistanceRange getDistanceRangeById(long id) {
        return distanceRangeDAO.getDistanceRangeById(id);
    }

    public boolean updateDistanceRange(DistanceRange distanceRange) {
        return distanceRangeDAO.updateDistanceRange(distanceRange);
    }

    public boolean deleteDistanceRange(long id) {
        return distanceRangeDAO.deleteDistanceRange(id);
    }
}