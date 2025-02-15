package org.project.mega_city_cab_service_app.dao;

import org.project.mega_city_cab_service_app.model.booking.Booking;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class BookingDAO {

    // Save a new booking
    public boolean addBooking(Booking booking) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO booking (customer_id, pickup_location, drop_location, booking_type, vehicle_id, driver_id, initial_km, final_km, pickup_time, return_time, days_needed) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, booking.getCustomerId());
                statement.setString(2, booking.getPickupLocation());
                statement.setString(3, booking.getDropLocation());
                statement.setString(4, booking.getBookingType());
                statement.setInt(5, booking.getVehicleId());
                statement.setObject(6, booking.getDriverId()); // Nullable
                statement.setInt(7, booking.getInitialKm());
                statement.setInt(8, booking.getFinalKm());
                statement.setTimestamp(9, Timestamp.valueOf(booking.getPickupTime()));
                statement.setTimestamp(10, Timestamp.valueOf(booking.getReturnTime()));
                statement.setInt(11, booking.getDaysNeeded());

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    booking.setOrderNo(generatedKeys.getInt(1));
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

    // Retrieve a booking by its order number
    public Booking findBookingByID(int orderNo) {
        String sql = "SELECT * FROM booking WHERE order_no = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderNo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String pickupLocation = resultSet.getString("pickup_location");
                String dropLocation = resultSet.getString("drop_location");
                String bookingType = resultSet.getString("booking_type");
                int vehicleId = resultSet.getInt("vehicle_id");
                Integer driverId = resultSet.getObject("driver_id", Integer.class); // Nullable
                int initialKm = resultSet.getInt("initial_km");
                int finalKm = resultSet.getInt("final_km");
                LocalDateTime pickupTime = resultSet.getTimestamp("pickup_time").toLocalDateTime();
                LocalDateTime returnTime = resultSet.getTimestamp("return_time").toLocalDateTime();
                int daysNeeded = resultSet.getInt("days_needed");

                Booking booking = new Booking(customerId, pickupLocation, dropLocation, bookingType, vehicleId, driverId, initialKm, finalKm, pickupTime, returnTime, daysNeeded);
                booking.setOrderNo(orderNo);
                return booking;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing booking
    public boolean updateBooking(Booking booking) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "UPDATE booking SET customer_id = ?, pickup_location = ?, drop_location = ?, booking_type = ?, vehicle_id = ?, driver_id = ?, initial_km = ?, final_km = ?, pickup_time = ?, return_time = ?, days_needed = ? WHERE order_no = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, booking.getCustomerId());
                statement.setString(2, booking.getPickupLocation());
                statement.setString(3, booking.getDropLocation());
                statement.setString(4, booking.getBookingType());
                statement.setInt(5, booking.getVehicleId());
                statement.setObject(6, booking.getDriverId()); // Nullable
                statement.setInt(7, booking.getInitialKm());
                statement.setInt(8, booking.getFinalKm());
                statement.setTimestamp(9, Timestamp.valueOf(booking.getPickupTime()));
                statement.setTimestamp(10, Timestamp.valueOf(booking.getReturnTime()));
                statement.setInt(11, booking.getDaysNeeded());
                statement.setInt(12, booking.getOrderNo());

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

    // Delete a booking by its order number
    public boolean deleteBooking(int orderNo) {
        String sql = "DELETE FROM booking WHERE order_no = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderNo);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper methods for transaction management
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