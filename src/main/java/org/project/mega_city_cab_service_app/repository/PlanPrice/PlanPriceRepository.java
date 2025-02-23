package org.project.mega_city_cab_service_app.repository.PlanPrice;



import org.project.mega_city_cab_service_app.model.PlanPrice.PlanPrice;

import java.util.List;

public interface PlanPriceRepository {
    boolean addPlanPrice(PlanPrice planPrice);
    List<PlanPrice> getAllPlanPrices();
    PlanPrice getPlanPriceById(long id);
    boolean updatePlanPrice(PlanPrice planPrice);
    boolean deletePlanPrice(long id);
}