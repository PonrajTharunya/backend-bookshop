package org.project.mega_city_cab_service_app.service.TaxService;

import org.project.mega_city_cab_service_app.factory.Interface.GenericFactory;
import org.project.mega_city_cab_service_app.factory.ManageTaxFactory.TaxFactory;
import org.project.mega_city_cab_service_app.factory.Registry.FactoryRegistry;

import org.project.mega_city_cab_service_app.model.tax.Tax;

public class TaxFactoryService {
    private final FactoryRegistry<Tax> registry;

    public TaxFactoryService() {
        this.registry = new FactoryRegistry<>();
        registry.registerFactory("tax", new TaxFactory());
    }

    public GenericFactory<Tax> getTaxFactory(String type) {
        GenericFactory<Tax> factory = registry.getFactory(type);
        if (factory == null) {
            throw new IllegalArgumentException("Invalid tax type: " + type);
        }
        return factory;
    }
}