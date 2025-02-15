package org.project.mega_city_cab_service_app.service.TaxService;

import org.project.mega_city_cab_service_app.factory.Interface.GenericFactory;
import org.project.mega_city_cab_service_app.model.tax.Tax;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class TaxRegistrationService {
    private final TaxService taxService;
    private final TaxFactoryService taxFactoryService;

    public TaxRegistrationService(TaxService taxService, TaxFactoryService taxFactoryService) {
        this.taxService = taxService;
        this.taxFactoryService = taxFactoryService;
    }

    public String registerTax(String jsonInput) {
        String type = JsonUtils.extractValueFromJson(jsonInput, "type"); // e.g., "tax"

        // Get the appropriate factory
        GenericFactory<Tax> factory = taxFactoryService.getTaxFactory(type);

        // Create the tax object
        Tax tax = factory.create(jsonInput);

        // Register the tax
        boolean isRegistered = taxService.addTax(tax);
        if (isRegistered) {
            return "{\"message\": \"Tax registered successfully!\"}";
        } else {
            return "{\"error\": \"Failed to register tax.\"}";
        }
    }
}