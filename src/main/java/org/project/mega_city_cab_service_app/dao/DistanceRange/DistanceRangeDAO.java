package org.project.mega_city_cab_service_app.dao.DistanceRange;


import org.project.mega_city_cab_service_app.model.Range.DistanceRange;
import org.project.mega_city_cab_service_app.repository.DistanceRange.DistanceRangeRepository;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DistanceRangeDAO implements DistanceRangeRepository {

    @Override
    public boolean addDistanceRange(DistanceRange distanceRange) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO distance_ranges (min_distance, max_distance) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, distanceRange.getMinDistance());
                statement.setInt(2, distanceRange.getMaxDistance());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        distanceRange.setId(generatedKeys.getLong(1)); // Set the auto-generated ID
                    }
                } else {
                    throw new SQLException("Failed to insert distance range.");
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
    public List<DistanceRange> getAllDistanceRanges() {
        List<DistanceRange> distanceRanges = new ArrayList<>();
        String sql = "SELECT id, min_distance, max_distance FROM distance_ranges";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DistanceRange distanceRange = new DistanceRange(
                        resultSet.getInt("min_distance"),
                        resultSet.getInt("max_distance")
                );
                distanceRange.setId(resultSet.getLong("id"));
                distanceRanges.add(distanceRange);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distanceRanges;
    }

    @Override
    public DistanceRange getDistanceRangeById(long id) {
        String sql = "SELECT id, min_distance, max_distance FROM distance_ranges WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                DistanceRange distanceRange = new DistanceRange(
                        resultSet.getInt("min_distance"),
                        resultSet.getInt("max_distance")
                );
                distanceRange.setId(id);
                return distanceRange;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateDistanceRange(DistanceRange distanceRange) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "UPDATE distance_ranges SET min_distance = ?, max_distance = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, distanceRange.getMinDistance());
                statement.setInt(2, distanceRange.getMaxDistance());
                statement.setLong(3, distanceRange.getId());

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
    public boolean deleteDistanceRange(long id) {
        String sql = "DELETE FROM distance_ranges WHERE id = ?";
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