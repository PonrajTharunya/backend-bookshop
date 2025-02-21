//package org.project.mega_city_cab_service_app.dao;
//
//import org.project.mega_city_cab_service_app.model.Parent.User;
//import org.project.mega_city_cab_service_app.factory.UserFactory;
//
//public class loginuserDAO {
//    public User findByEmail(String email) {
//        // Simulate database lookup
//        if ("admin@example.com".equals(email)) {
//            return UserFactory.createUser(email, "admin123", "ADMIN");
//        } else if ("employee@example.com".equals(email)) {
//            return UserFactory.createUser(email, "employee123", "EMPLOYEE");
//        } else if ("customer@example.com".equals(email)) {
//            return UserFactory.createUser(email, "customer123", "CUSTOMER");
//        }
//        return null; // User not found
//    }
//}

package org.project.mega_city_cab_service_app.dao;

import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.model.person.Admin;
import org.project.mega_city_cab_service_app.model.person.Customer;
import org.project.mega_city_cab_service_app.model.person.Driver;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginuserDAO {

    private final DBConnection dbConnection;

    public loginuserDAO() {
        this.dbConnection = DBConnection.getInstance();
    }

    public Person findByUsername(String username) {
        String sql = "SELECT * FROM person WHERE username = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String type = resultSet.getString("type");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String mobile = resultSet.getString("mobile");
                String password = resultSet.getString("password");

                // Return a Person object based on the type
                switch (type.toUpperCase()) {
                    case "ADMIN":
                        return new  Admin(name, address, mobile, username, password);
                    case "CUSTOMER":
                        int rating = resultSet.getInt("rating");
                        String description = resultSet.getString("description");
                        return new Customer(name, address, mobile, username, password, rating, description);
                    case "DRIVER":
                        double salary = resultSet.getDouble("salary");
                        int experience = resultSet.getInt("experience");
                        String licenseNumber = resultSet.getString("license_number");
                        String licenseType = resultSet.getString("license_type");
                        return new Driver(name, address, mobile, username, password, salary, experience, licenseNumber, licenseType);
                    default:
                        throw new IllegalArgumentException("Invalid person type: " + type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }
}