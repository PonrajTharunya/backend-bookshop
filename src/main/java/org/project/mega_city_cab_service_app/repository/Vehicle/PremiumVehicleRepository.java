package org.project.mega_city_cab_service_app.repository.Vehicle;

import org.project.mega_city_cab_service_app.model.vehical.PremiumVehicle;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PremiumVehicleRepository implements VehicleRepository {
    private final DBConnection dbConnection;

    public PremiumVehicleRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean save(Vehicle vehicle) {
        if (!(vehicle instanceof PremiumVehicle premiumVehicle)) {
            throw new IllegalArgumentException("Invalid vehicle type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Insert into `vehicle` table
            String vehicleSql = "INSERT INTO vehicle (type, name, model, color, year, registration_number, seating_capacity) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement vehicleStatement = connection.prepareStatement(vehicleSql, Statement.RETURN_GENERATED_KEYS)) {
                vehicleStatement.setString(1, vehicle.getType());
                vehicleStatement.setString(2, vehicle.getName());
                vehicleStatement.setString(3, vehicle.getModel());
                vehicleStatement.setString(4, vehicle.getColor());
                vehicleStatement.setInt(5, vehicle.getYear());
                vehicleStatement.setInt(6, vehicle.getRegistrationNumber());
                vehicleStatement.setInt(7, vehicle.getSeatingCapacity());
                vehicleStatement.executeUpdate();

                ResultSet generatedKeys = vehicleStatement.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new SQLException("Failed to retrieve generated keys.");
                }
                int vehicleId = generatedKeys.getInt(1);

                // Insert into `premium_vehicle` table
                String premiumSql = "INSERT INTO premium_vehicle (id, has_wifi) VALUES (?, ?)";
                try (PreparedStatement premiumStatement = connection.prepareStatement(premiumSql)) {
                    premiumStatement.setInt(1, vehicleId);
                    premiumStatement.setBoolean(2, premiumVehicle.hasWiFi());
                    System.out.println(premiumVehicle.hasWiFi());
                    premiumStatement.executeUpdate();
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
            return false;
        } finally {
            resetAutoCommit(connection);
        }
    }

    @Override
    public Vehicle findByID(int vehicleId) {
        String vehicleSql = "SELECT * FROM vehicle WHERE id = ?";
        String premiumSql = "SELECT * FROM premium_vehicle WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement vehicleStatement = connection.prepareStatement(vehicleSql);
             PreparedStatement premiumStatement = connection.prepareStatement(premiumSql)) {

            // Query the `vehicle` table
            vehicleStatement.setInt(1, vehicleId);
            ResultSet vehicleResultSet = vehicleStatement.executeQuery();

            // Check if a result was found in the `vehicle` table
            if (!vehicleResultSet.next()) {
                return null; // No vehicle found
            }

            // Extract fields from the `vehicle` table
            int id = vehicleResultSet.getInt("id"); // Retrieve the ID from the database
            String name = vehicleResultSet.getString("name");
            String model = vehicleResultSet.getString("model");
            String color = vehicleResultSet.getString("color");
            int year = vehicleResultSet.getInt("year");
            int registrationNumber = vehicleResultSet.getInt("registration_number");
            int seatingCapacity = vehicleResultSet.getInt("seating_capacity");

            // Query the `premium_vehicle` table
            premiumStatement.setInt(1, vehicleId);
            ResultSet premiumResultSet = premiumStatement.executeQuery();

            // Check if a result was found in the `premium_vehicle` table
            if (!premiumResultSet.next()) {
                return null; // No premium vehicle found
            }

            // Extract fields from the `premium_vehicle` table
            boolean hasWiFi = premiumResultSet.getBoolean("has_wifi");

            // Create the PremiumVehicle object
            PremiumVehicle vehicle = new PremiumVehicle(name, model, color, year, registrationNumber, seatingCapacity, hasWiFi);

            // Set the ID fetched from the database
            vehicle.setId(id);

            // Return the fully initialized PremiumVehicle object
            return vehicle;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null in case of an error
    }

    @Override
    public boolean update(Vehicle vehicle) {
        if (!(vehicle instanceof PremiumVehicle premiumVehicle)) {
            throw new IllegalArgumentException("Invalid vehicle type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Update `vehicle` table
            String vehicleSql = "UPDATE vehicle SET name = ?, model = ?, color = ?, year = ?, registration_number = ?, seating_capacity = ? " +
                    "WHERE id = ?";
            try (PreparedStatement vehicleStatement = connection.prepareStatement(vehicleSql)) {
                vehicleStatement.setString(1, vehicle.getName());
                vehicleStatement.setString(2, vehicle.getModel());
                vehicleStatement.setString(3, vehicle.getColor());
                vehicleStatement.setInt(4, vehicle.getYear());
                vehicleStatement.setInt(5, vehicle.getRegistrationNumber());
                vehicleStatement.setInt(6, vehicle.getSeatingCapacity());
                vehicleStatement.setInt(7, vehicle.getId()); // Use `id` here
                vehicleStatement.executeUpdate();
            }

            // Update `premium_vehicle` table
            String premiumSql = "UPDATE premium_vehicle SET has_wifi = ? WHERE id = ?";
            try (PreparedStatement premiumStatement = connection.prepareStatement(premiumSql)) {
                premiumStatement.setBoolean(1, premiumVehicle.hasWiFi());
                premiumStatement.setInt(2, vehicle.getId()); // Use `id` here
                premiumStatement.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
            return false;
        } finally {
            resetAutoCommit(connection);
        }
    }

    @Override
    public boolean delete(int vehicleId) {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Delete from `premium_vehicle` table
            String premiumSql = "DELETE FROM premium_vehicle WHERE id = ?";
            try (PreparedStatement premiumStatement = connection.prepareStatement(premiumSql)) {
                premiumStatement.setInt(1, vehicleId);
                premiumStatement.executeUpdate();
            }

            // Delete from `vehicle` table
            String vehicleSql = "DELETE FROM vehicle WHERE id = ?";
            try (PreparedStatement vehicleStatement = connection.prepareStatement(vehicleSql)) {
                vehicleStatement.setInt(1, vehicleId);
                vehicleStatement.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
            return false;
        } finally {
            resetAutoCommit(connection);
        }
    }
    @Override
    public List<Vehicle> findAll() {
        String vehicleSql = "SELECT * FROM vehicle WHERE type = 'Premium'";
        String premiumSql = "SELECT * FROM premium_vehicle WHERE id IN (SELECT id FROM vehicle WHERE type = 'Premium')";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement vehicleStatement = connection.prepareStatement(vehicleSql);
             PreparedStatement premiumStatement = connection.prepareStatement(premiumSql)) {

            ResultSet vehicleResultSet = vehicleStatement.executeQuery();
            ResultSet premiumResultSet = premiumStatement.executeQuery();

            // Iterate through both result sets simultaneously
            while (vehicleResultSet.next() && premiumResultSet.next()) {
                // Extract fields from the `vehicle` table
                int id = vehicleResultSet.getInt("id"); // Retrieve the ID from the database
                String name = vehicleResultSet.getString("name");
                String model = vehicleResultSet.getString("model");
                String color = vehicleResultSet.getString("color");
                int year = vehicleResultSet.getInt("year");
                int registrationNumber = vehicleResultSet.getInt("registration_number");
                int seatingCapacity = vehicleResultSet.getInt("seating_capacity");

                // Extract fields from the `premium_vehicle` table
                boolean hasWiFi = premiumResultSet.getBoolean("has_wifi");

                // Create the PremiumVehicle object
                PremiumVehicle vehicle = new PremiumVehicle(name, model, color, year, registrationNumber, seatingCapacity, hasWiFi);

                // Set the ID fetched from the database
                vehicle.setId(id);

                // Add the fully initialized PremiumVehicle object to the list
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void resetAutoCommit(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}