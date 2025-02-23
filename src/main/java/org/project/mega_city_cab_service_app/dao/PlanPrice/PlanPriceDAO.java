package org.project.mega_city_cab_service_app.dao.PlanPrice;


import org.project.mega_city_cab_service_app.model.PlanPrice.PlanPrice;
import org.project.mega_city_cab_service_app.repository.PlanPrice.PlanPriceRepository;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanPriceDAO implements PlanPriceRepository {

    @Override
    public boolean addPlanPrice(PlanPrice planPrice) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO plan_price (distance_range_id, price, extra_km_price, discount, vehicle_plan_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, planPrice.getDistanceRangeId());
                statement.setDouble(2, planPrice.getPrice());
                statement.setDouble(3, planPrice.getExtraKmPrice());
                statement.setDouble(4, planPrice.getDiscount());
                statement.setInt(5, planPrice.getVehiclePlanId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        planPrice.setId(generatedKeys.getLong(1)); // Set the auto-generated ID
                    }
                } else {
                    throw new SQLException("Failed to insert plan price.");
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
    public List<PlanPrice> getAllPlanPrices() {
        List<PlanPrice> planPrices = new ArrayList<>();
        String sql = "SELECT id, distance_range_id, price, extra_km_price, discount, vehicle_plan_id FROM plan_price";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PlanPrice planPrice = new PlanPrice(
                        resultSet.getLong("distance_range_id"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("extra_km_price"),
                        resultSet.getDouble("discount"),
                        resultSet.getInt("vehicle_plan_id")
                );
                planPrice.setId(resultSet.getLong("id"));
                planPrices.add(planPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planPrices;
    }

    @Override
    public PlanPrice getPlanPriceById(long id) {
        String sql = "SELECT id, distance_range_id, price, extra_km_price, discount, vehicle_plan_id FROM plan_price WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                PlanPrice planPrice = new PlanPrice(
                        resultSet.getLong("distance_range_id"),
                        resultSet.getDouble("price"),
                        resultSet.getDouble("extra_km_price"),
                        resultSet.getDouble("discount"),
                        resultSet.getInt("vehicle_plan_id")
                );
                planPrice.setId(id);
                return planPrice;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updatePlanPrice(PlanPrice planPrice) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "UPDATE plan_price SET distance_range_id = ?, price = ?, extra_km_price = ?, discount = ?, vehicle_plan_id = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, planPrice.getDistanceRangeId());
                statement.setDouble(2, planPrice.getPrice());
                statement.setDouble(3, planPrice.getExtraKmPrice());
                statement.setDouble(4, planPrice.getDiscount());
                statement.setInt(5, planPrice.getVehiclePlanId());
                statement.setLong(6, planPrice.getId());

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
    public boolean deletePlanPrice(long id) {
        String sql = "DELETE FROM plan_price WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
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