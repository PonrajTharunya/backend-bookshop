package org.project.mega_city_cab_service_app.dao.VehiclePlan;

import org.project.mega_city_cab_service_app.model.VehiclePlan;

import org.project.mega_city_cab_service_app.repository.VehiclePlan.VehiclePlanRepository;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiclePlanDAO implements VehiclePlanRepository {

    @Override
    public boolean addVehiclePlan(VehiclePlan vehiclePlan) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO vehicle_plan (plan_name) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, vehiclePlan.getPlanName());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        vehiclePlan.setId(generatedKeys.getInt(1)); // Set the auto-generated ID
                    }
                } else {
                    throw new SQLException("Failed to insert vehicle plan.");
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
    public List<VehiclePlan> getAllVehiclePlans() {
        List<VehiclePlan> vehiclePlans = new ArrayList<>();
        String sql = "SELECT id, plan_name FROM vehicle_plan";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VehiclePlan vehiclePlan = new VehiclePlan(resultSet.getString("plan_name"));
                vehiclePlan.setId(resultSet.getInt("id"));
                vehiclePlans.add(vehiclePlan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiclePlans;
    }

    @Override
    public VehiclePlan getVehiclePlanById(int id) {
        String sql = "SELECT id, plan_name FROM vehicle_plan WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                VehiclePlan vehiclePlan = new VehiclePlan(resultSet.getString("plan_name"));
                vehiclePlan.setId(id);
                return vehiclePlan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateVehiclePlan(VehiclePlan vehiclePlan) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "UPDATE vehicle_plan SET plan_name = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, vehiclePlan.getPlanName());
                statement.setInt(2, vehiclePlan.getId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            rollback(connection);
            e.printStackTrace();
        } finally {
            resetAutoCommit(connection);
        }
        return false;
    }

    @Override
    public boolean deleteVehiclePlan(int id) {
        String sql = "DELETE FROM vehicle_plan WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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