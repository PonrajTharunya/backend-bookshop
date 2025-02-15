package org.project.mega_city_cab_service_app.factory.Registry;

import org.project.mega_city_cab_service_app.factory.Interface.GenericFactory;

import java.util.HashMap;
import java.util.Map;

public class FactoryRegistry<T> {
    private final Map<String, GenericFactory<T>> registry = new HashMap<>();

    public void registerFactory(String type, GenericFactory<T> factory) {
        registry.put(type, factory);
    }

    public GenericFactory<T> getFactory(String type) {
        return registry.get(type);
    }
}