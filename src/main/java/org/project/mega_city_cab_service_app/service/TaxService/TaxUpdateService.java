package org.project.mega_city_cab_service_app.service.TaxService;

import org.project.mega_city_cab_service_app.factory.Interface.GenericFactory;
import org.project.mega_city_cab_service_app.model.tax.Tax;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class TaxUpdateService {
    private final TaxService taxService;
    private final TaxFactoryService taxFactoryService;

    public TaxUpdateService(TaxService taxService, TaxFactoryService taxFactoryService) {
        this.taxService = taxService;
        this.taxFactoryService = taxFactoryService;
    }

    /**
     * Updates an existing tax.
     *
     * @param taxId    The ID of the tax to update.
     * @param jsonInput The JSON input containing updated tax details.
     * @return A JSON response indicating success or failure.
     */
    public String updateTax(int taxId, String jsonInput) {
        // Use the factory to create a Tax object from the JSON input
        GenericFactory<Tax> factory = taxFactoryService.getTaxFactory("tax");
        Tax updatedTax = factory.create(jsonInput);

        // Set the tax ID explicitly (since it's not part of the JSON input)
        updatedTax.setTaxId(taxId);

        // Delegate the update operation to the TaxService
        boolean isUpdated = taxService.updateTax(updatedTax);
        if (isUpdated) {
            return "{\"message\": \"Tax updated successfully!\"}";
        } else {
            return "{\"error\": \"Failed to update tax.\"}";
        }
    }
}