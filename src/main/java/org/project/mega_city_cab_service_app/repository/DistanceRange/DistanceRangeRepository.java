package org.project.mega_city_cab_service_app.repository.DistanceRange;



import org.project.mega_city_cab_service_app.model.Range.DistanceRange;

import java.util.List;

public interface DistanceRangeRepository {
    boolean addDistanceRange(DistanceRange distanceRange);
    List<DistanceRange> getAllDistanceRanges();
    DistanceRange getDistanceRangeById(long id);
    boolean updateDistanceRange(DistanceRange distanceRange);
    boolean deleteDistanceRange(long id);
}