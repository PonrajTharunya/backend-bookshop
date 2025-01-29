package org.example.mega_city_cab_service_app.util;

import java.sql.Connection;

public interface DatabaseConnection {
    Connection getConnection();
    void closeConnection();
}
//Dependency Inversion Principle - DIP
//we only need to create a new class without modifying the existing code.
// so we can go with mongodb , mysql, or PSQL
