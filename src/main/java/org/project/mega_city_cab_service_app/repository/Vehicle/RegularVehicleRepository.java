package org.project.mega_city_cab_service_app.repository.Vehicle;

import org.project.mega_city_cab_service_app.model.vehical.RegularVehicle;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;

public class RegularVehicleRepository implements VehicleRepository {
    private final DBConnection dbConnection;

    public RegularVehicleRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean save(Vehicle vehicle) {
        if (!(vehicle instanceof RegularVehicle regularVehicle)) {
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

                // Insert into `regular_vehicle` table
                String regularSql = "INSERT INTO regular_vehicle (id) VALUES (?)";
                try (PreparedStatement regularStatement = connection.prepareStatement(regularSql)) {
                    regularStatement.setInt(1, vehicleId);
                    regularStatement.executeUpdate();
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
        String regularSql = "SELECT * FROM regular_vehicle WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement vehicleStatement = connection.prepareStatement(vehicleSql);
             PreparedStatement regularStatement = connection.prepareStatement(regularSql)) {

            vehicleStatement.setInt(1, vehicleId);
            ResultSet vehicleResultSet = vehicleStatement.executeQuery();

            if (!vehicleResultSet.next()) {
                return null; // No vehicle found
            }

            String name = vehicleResultSet.getString("name");
            String model = vehicleResultSet.getString("model");
            String color = vehicleResultSet.getString("color");
            int year = vehicleResultSet.getInt("year");
            int registrationNumber = vehicleResultSet.getInt("registration_number");
            int seatingCapacity = vehicleResultSet.getInt("seating_capacity");

            regularStatement.setInt(1, vehicleId);
            ResultSet regularResultSet = regularStatement.executeQuery();

            if (!regularResultSet.next()) {
                return null; // No regular vehicle found
            }

            return new RegularVehicle(name, model, color, year, registrationNumber, seatingCapacity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Vehicle vehicle) {
        if (!(vehicle instanceof RegularVehicle regularVehicle)) {
            throw new IllegalArgumentException("Invalid vehicle type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

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

            String regularSql = "DELETE FROM regular_vehicle WHERE id = ?";
            try (PreparedStatement regularStatement = connection.prepareStatement(regularSql)) {
                regularStatement.setInt(1, vehicleId);
                regularStatement.executeUpdate();
            }

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