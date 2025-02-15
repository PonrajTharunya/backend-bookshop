package org.project.mega_city_cab_service_app.repository.Vehicle;

import org.project.mega_city_cab_service_app.model.vehical.VIPVehicle;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;

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

            vipStatement.setInt(1, vehicleId);
            ResultSet vipResultSet = vipStatement.executeQuery();

            if (!vipResultSet.next()) {
                return null; // No VIP vehicle found
            }

            boolean hasChauffeurService = vipResultSet.getBoolean("has_chauffeur_service");

            return new VIPVehicle(name, model, color, year, registrationNumber, seatingCapacity, hasChauffeurService);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Vehicle vehicle) {
        if (!(vehicle instanceof VIPVehicle vipVehicle)) {
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
                vehicleStatement.setInt(7, vehicle.getRegistrationNumber()); // Assuming registration number is unique
                vehicleStatement.executeUpdate();
            }

            // Update `vip_vehicle` table
            String vipSql = "UPDATE vip_vehicle SET has_chauffeur_service = ? WHERE id = ?";
            try (PreparedStatement vipStatement = connection.prepareStatement(vipSql)) {
                vipStatement.setBoolean(1, vipVehicle.hasChauffeurService());
                vipStatement.setInt(2, vehicle.getRegistrationNumber());
                vipStatement.executeUpdate();
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