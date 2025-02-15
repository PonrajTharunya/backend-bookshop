package org.project.mega_city_cab_service_app.service.TaxService;

import org.project.mega_city_cab_service_app.model.tax.Tax;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class TaxDeletionService {
    private final TaxService taxService;

    public TaxDeletionService(TaxService taxService) {
        this.taxService = taxService;
    }

    /**
     * Deletes a tax by its ID.
     *
     * @param taxId The ID of the tax to delete.
     * @return A JSON response indicating success or failure.
     */
    public String deleteTax(int taxId) {
        boolean isDeleted = taxService.deleteTax(taxId);
        if (isDeleted) {
            return "{\"message\": \"Tax deleted successfully!\"}";
        } else {
            return "{\"error\": \"Failed to delete tax.\"}";
        }
    }
}