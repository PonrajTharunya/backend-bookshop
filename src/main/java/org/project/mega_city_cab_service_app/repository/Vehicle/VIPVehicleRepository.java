package org.project.mega_city_cab_service_app.repository.Vehicle;

import org.project.mega_city_cab_service_app.model.vehical.VIPVehicle;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VIPVehicleRepository implements VehicleRepository {
    private final DBConnection dbConnection;

    public VIPVehicleRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean save(Vehicle vehicle) {
        if (!(vehicle instanceof VIPVehicle vipVehicle)) {
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

                // Insert into `vip_vehicle` table
                String vipSql = "INSERT INTO vip_vehicle (id, has_chauffeur_service) VALUES (?, ?)";
                try (PreparedStatement vipStatement = connection.prepareStatement(vipSql)) {
                    vipStatement.setInt(1, vehicleId);
                    vipStatement.setBoolean(2, vipVehicle.hasChauffeurService());
                    vipStatement.executeUpdate();
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
        String vipSql = "SELECT * FROM vip_vehicle WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement vehicleStatement = connection.prepareStatement(vehicleSql);
             PreparedStatement vipStatement = connection.prepareStatement(vipSql)) {

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

            // Query the `vip_vehicle` table
            vipStatement.setInt(1, vehicleId);
            ResultSet vipResultSet = vipStatement.executeQuery();

            // Check if a result was found in the `vip_vehicle` table
            if (!vipResultSet.next()) {
                return null; // No VIP vehicle found
            }

            // Extract fields from the `vip_vehicle` table
            boolean hasChauffeurService = vipResultSet.getBoolean("has_chauffeur_service");

            // Create the VIPVehicle object
            VIPVehicle vehicle = new VIPVehicle(name, model, color, year, registrationNumber, seatingCapacity, hasChauffeurService);

            // Set the ID fetched from the database
            vehicle.setId(id);

            // Return the fully initialized VIPVehicle object
            return vehicle;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null in case of an error
    }

    @Override
    public boolean update(Vehicle vehicle) {
        // Ensure the vehicle is of type VIPVehicle
        if (!(vehicle instanceof VIPVehicle vipVehicle)) {
            throw new IllegalArgumentException("Invalid vehicle type");
        }

        Connection connection = null;
        try {
            // Get database connection and disable auto-commit
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

            // Update `vip_vehicle` table
            String vipSql = "UPDATE vip_vehicle SET has_chauffeur_service = ? WHERE id = ?";
            try (PreparedStatement vipStatement = connection.prepareStatement(vipSql)) {
                vipStatement.setBoolean(1, vipVehicle.hasChauffeurService());
                vipStatement.setInt(2, vehicle.getId()); // Use `id` here
                vipStatement.executeUpdate();
            }

            // Commit the transaction
            connection.commit();
            return true;
        } catch (SQLException e) {
            // Rollback in case of an error
            rollback(connection);
            e.printStackTrace();
            return false;
        } finally {
            // Reset auto-commit to true
            resetAutoCommit(connection);
        }
    }
    @Override
    public List<Vehicle> findAll() {
        String vehicleSql = "SELECT * FROM vehicle WHERE type = 'VIP'";
        String vipSql = "SELECT * FROM vip_vehicle WHERE id IN (SELECT id FROM vehicle WHERE type = 'VIP')";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement vehicleStatement = connection.prepareStatement(vehicleSql);
             PreparedStatement vipStatement = connection.prepareStatement(vipSql)) {

            ResultSet vehicleResultSet = vehicleStatement.executeQuery();
            ResultSet vipResultSet = vipStatement.executeQuery();

            // Iterate through both result sets simultaneously
            while (vehicleResultSet.next() && vipResultSet.next()) {
                // Extract fields from the `vehicle` table
                int id = vehicleResultSet.getInt("id"); // Retrieve the ID from the database
                String name = vehicleResultSet.getString("name");
                String model = vehicleResultSet.getString("model");
                String color = vehicleResultSet.getString("color");
                int year = vehicleResultSet.getInt("year");
                int registrationNumber = vehicleResultSet.getInt("registration_number");
                int seatingCapacity = vehicleResultSet.getInt("seating_capacity");

                // Extract fields from the `vip_vehicle` table
                boolean hasChauffeurService = vipResultSet.getBoolean("has_chauffeur_service");

                // Create the VIPVehicle object
                VIPVehicle vehicle = new VIPVehicle(name, model, color, year, registrationNumber, seatingCapacity, hasChauffeurService);

                // Set the ID fetched from the database
                vehicle.setId(id);

                // Add the fully initialized VIPVehicle object to the list
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    @Override
    public boolean delete(int vehicleId) {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            // Delete from `vip_vehicle` table
            String vipSql = "DELETE FROM vip_vehicle WHERE id = ?";
            try (PreparedStatement vipStatement = connection.prepareStatement(vipSql)) {
                vipStatement.setInt(1, vehicleId);
                vipStatement.executeUpdate();
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