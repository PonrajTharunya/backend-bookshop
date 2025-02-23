package org.project.mega_city_cab_service_app.service.PlanPriceService;

import org.project.mega_city_cab_service_app.dao.PlanPrice.PlanPriceDAO;
import org.project.mega_city_cab_service_app.model.PlanPrice.PlanPrice;


import java.util.List;

public class PlanPriceService {
    private final PlanPriceDAO planPriceDAO;

    public PlanPriceService(PlanPriceDAO planPriceDAO) {
        this.planPriceDAO = planPriceDAO;
    }

    public boolean addPlanPrice(PlanPrice planPrice) {
        return planPriceDAO.addPlanPrice(planPrice);
    }

    public List<PlanPrice> getAllPlanPrices() {
        return planPriceDAO.getAllPlanPrices();
    }

    public PlanPrice getPlanPriceById(long id) {
        return planPriceDAO.getPlanPriceById(id);
    }

    public boolean updatePlanPrice(PlanPrice planPrice) {
        return planPriceDAO.updatePlanPrice(planPrice);
    }

    public boolean deletePlanPrice(long id) {
        return planPriceDAO.deletePlanPrice(id);
    }
}