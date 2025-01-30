package org.example.mega_city_cab_service_app.factory;

import java.util.HashMap;
import java.util.Map;

public class FactoryRegistry {
    private static final Map<String, PersonFactory> registry = new HashMap<>();

    public static void registerFactory(String type, PersonFactory factory) {
        registry.put(type.toUpperCase(), factory);
    }

    public static PersonFactory getFactory(String type) {
        return registry.get(type.toUpperCase());
    }
}