package org.project.mega_city_cab_service_app.factory.Interface;

public interface GenericFactory<T> {
    T create(String jsonInput);
}
