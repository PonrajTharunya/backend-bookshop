package org.project.mega_city_cab_service_app.repository.Vehicle;

import org.project.mega_city_cab_service_app.model.vehical.RegularVehicle;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegularVehicleRepository implements VehicleRepository {
    private final DBConnection dbConnection;

    public RegularVehicleRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean save(Vehicle vehicle) {
        if (!(vehicle instanceof RegularVehicle)) {
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

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement vehicleStatement = connection.prepareStatement(vehicleSql)) {

            // Set the parameter for the query
            vehicleStatement.setInt(1, vehicleId);

            // Execute the query
            ResultSet vehicleResultSet = vehicleStatement.executeQuery();

            // Check if a result was found
            if (!vehicleResultSet.next()) {
                return null; // No vehicle found
            }

            // Extract fields from the result set
            int id = vehicleResultSet.getInt("id"); // Retrieve the ID from the database
            String name = vehicleResultSet.getString("name");
            String model = vehicleResultSet.getString("model");
            String color = vehicleResultSet.getString("color");
            int year = vehicleResultSet.getInt("year");
            int registrationNumber = vehicleResultSet.getInt("registration_number");
            int seatingCapacity = vehicleResultSet.getInt("seating_capacity");

            // Create the vehicle object
            RegularVehicle vehicle = new RegularVehicle(name, model, color, year, registrationNumber, seatingCapacity);

            // Set the ID fetched from the database
            vehicle.setId(id);

            // Return the fully initialized vehicle object
            return vehicle;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null in case of an error
    }

    @Override
    public boolean update(Vehicle vehicle) {
        if (!(vehicle instanceof RegularVehicle)) {
            throw new IllegalArgumentException("Invalid vehicle type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Update `vehicle` table
            String sql = "UPDATE vehicle SET name = ?, model = ?, color = ?, year = ?, registration_number = ?, seating_capacity = ? " +
                    "WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, vehicle.getName());
                statement.setString(2, vehicle.getModel());
                statement.setString(3, vehicle.getColor());
                statement.setInt(4, vehicle.getYear());
                statement.setInt(5, vehicle.getRegistrationNumber());
                statement.setInt(6, vehicle.getSeatingCapacity());
                statement.setInt(7, vehicle.getRegistrationNumber()); // Assuming registration number is unique
                statement.executeUpdate();
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
        String sql = "SELECT * FROM vehicle WHERE type = 'Regular'";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Extract fields from the result set
                int id = resultSet.getInt("id"); // Retrieve the ID from the database
                String name = resultSet.getString("name");
                String model = resultSet.getString("model");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                int registrationNumber = resultSet.getInt("registration_number");
                int seatingCapacity = resultSet.getInt("seating_capacity");

                // Create the vehicle object
                RegularVehicle vehicle = new RegularVehicle(name, model, color, year, registrationNumber, seatingCapacity);

                // Set the ID fetched from the database
                vehicle.setId(id);

                // Add the vehicle to the list
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