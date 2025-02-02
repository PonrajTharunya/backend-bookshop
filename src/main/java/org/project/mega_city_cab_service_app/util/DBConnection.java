package org.project.mega_city_cab_service_app.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection implements DatabaseConnection {
    private static DBConnection instance;
    private Connection connection;

    private DBConnection() {
        try {

            Properties properties = new Properties();
            //loading db properties which we store from our resources->properties file
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            if (input == null) {
                throw new RuntimeException("db.properties file not found");
            }
            properties.load(input);

            Class.forName(properties.getProperty("db.driver"));
            //connect to mysql
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );

        } catch (Exception e) {
            throw new RuntimeException("Database Connection Failed!", e);
        }
    }

// create new instance
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
        return connection;
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}