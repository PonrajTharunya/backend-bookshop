package org.project.mega_city_cab_service_app.factory.ManageTaxFactory;

import org.project.mega_city_cab_service_app.factory.Interface.GenericFactory;
import org.project.mega_city_cab_service_app.model.tax.Tax;
import org.project.mega_city_cab_service_app.util.JsonUtils;

public class TaxFactory implements GenericFactory<Tax> {

    @Override
    public Tax create(String jsonInput) {
        String description = JsonUtils.extractValueFromJson(jsonInput, "description");
        double taxRate = Double.parseDouble(JsonUtils.extractValueFromJson(jsonInput, "tax_rate"));
        boolean status = Boolean.parseBoolean(JsonUtils.extractValueFromJson(jsonInput, "status"));

        return new Tax(description, taxRate, status);
    }
}