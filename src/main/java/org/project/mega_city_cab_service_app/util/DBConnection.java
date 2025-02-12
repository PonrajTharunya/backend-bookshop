//    package org.project.mega_city_cab_service_app.util;
//
//
//    import java.sql.Connection;
//    import java.sql.DriverManager;
//    import java.sql.SQLException;
//    import java.util.Properties;
//    import java.io.InputStream;
//
//    public class DBConnection implements DatabaseConnection {
//        private static DBConnection instance;
//        private Connection connection;
//
//        private DBConnection() {
//            try {
//
//                Properties properties = new Properties();
//                //loading db properties which we store from our resources->properties file
//                InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
//                if (input == null) {
//                    throw new RuntimeException("db.properties file not found");
//                }
//                properties.load(input);
//
//                Class.forName(properties.getProperty("db.driver"));
//                //connect to mysql
//                connection = DriverManager.getConnection(
//                        properties.getProperty("db.url"),
//                        properties.getProperty("db.user"),
//                        properties.getProperty("db.password")
//                );
//
//            } catch (Exception e) {
//                throw new RuntimeException("Database Connection Failed!", e);
//            }
//        }
//
//    // create new instance
//        public static DBConnection getInstance() {
//            if (instance == null) {
//                synchronized (DBConnection.class) {
//                    if (instance == null) {
//                        instance = new DBConnection();
//                    }
//                }
//            }
//            return instance;
//        }
//
//        @Override
//        public Connection getConnection() {
//            return connection;
//        }
//
//        @Override
//        public void closeConnection() {
//            try {
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
package org.project.mega_city_cab_service_app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection implements DatabaseConnection {
    private static DBConnection instance;
    private Connection connection;

    // Private constructor to enforce singleton pattern
    private DBConnection() {
        establishConnection();
    }

    // Singleton instance creation
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        // Validate the connection before returning it
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(5)) { // 5-second timeout for validation
                System.out.println("Re-establishing database connection...");
                establishConnection(); // Re-establish the connection if it's invalid
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve a valid database connection", e);
        }
        return connection;
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to establish a new connection
    private void establishConnection() {
        try {
            Properties properties = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("db.properties file not found");
            }
            properties.load(input);

            // Load the JDBC driver and establish the connection
            Class.forName(properties.getProperty("db.driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new RuntimeException("Database Connection Failed!", e);
        }
    }
}