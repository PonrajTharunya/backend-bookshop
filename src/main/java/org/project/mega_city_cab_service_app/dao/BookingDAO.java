package org.project.mega_city_cab_service_app.dao;

import org.project.mega_city_cab_service_app.dao.DistanceRange.DistanceRangeDAO;
import org.project.mega_city_cab_service_app.dao.PlanPrice.PlanPriceDAO;
import org.project.mega_city_cab_service_app.dao.VehiclePlan.VehiclePlanDAO;
import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.model.PlanPrice.PlanPrice;
import org.project.mega_city_cab_service_app.model.Range.DistanceRange;
import org.project.mega_city_cab_service_app.model.VehiclePlan;
import org.project.mega_city_cab_service_app.model.booking.Booking;
import org.project.mega_city_cab_service_app.model.person.Customer;
import org.project.mega_city_cab_service_app.model.person.Employee;
import org.project.mega_city_cab_service_app.model.person.Driver;
import org.project.mega_city_cab_service_app.model.tax.Tax;
import org.project.mega_city_cab_service_app.model.vehical.PremiumVehicle;
import org.project.mega_city_cab_service_app.model.vehical.VIPVehicle;
import org.project.mega_city_cab_service_app.repository.Person.CustomerRepository;
import org.project.mega_city_cab_service_app.service.PersonService.PersonRetrievalService;
import org.project.mega_city_cab_service_app.service.PersonService.PersonService;
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

            String sql = "INSERT INTO booking (customer_id, pickup_location, drop_location, booking_type, vehicle_id, driver_id, initial_km, final_km, pickup_time, return_time, days_needed,tax_id,employee_id,package_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
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
                statement.setInt(12,booking.getTax_id());
                statement.setInt(13,booking.getEmployee_id());
                statement.setInt(14,booking.getPackage_id());
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
                int tax_id = resultSet.getInt("tax_id");
                int employee_id = resultSet.getInt("employee_id");
                int package_id = resultSet.getInt("package_id");

                Booking booking = new Booking(customerId, pickupLocation, dropLocation, bookingType, vehicleId, driverId, initialKm, finalKm, pickupTime, returnTime, daysNeeded, tax_id, employee_id, package_id);
                booking.setOrderNo(orderNo);

                // Fetch the associated Tax data
                TaxDAO taxDAO = new TaxDAO();
                Tax tax = taxDAO.findTaxById(tax_id);
                booking.setTax(tax);

                // Fetch the associated Customer, Driver, and Employee data
                PersonDAO personDAO = new PersonDAO(DBConnection.getInstance());
                booking.setCustomer((Customer) personDAO.findPersonById(customerId));
                if (driverId != null) {
                    booking.setDriver((Driver) personDAO.findPersonById(driverId));
                }
                booking.setEmployee((Employee) personDAO.findPersonById(employee_id));



                // Fetch the associated VehiclePlan, DistanceRange, and PlanPrice data
                PlanPriceDAO planPriceDAO = new PlanPriceDAO();
                VehiclePlanDAO vehiclePlanDAO = new VehiclePlanDAO();
                DistanceRangeDAO distanceRangeDAO = new DistanceRangeDAO();

                PlanPrice planPrice = planPriceDAO.getPlanPriceById(package_id);
                if (planPrice != null) {
                    booking.setPlanPrice(planPrice);

                    VehiclePlan vehiclePlan = vehiclePlanDAO.getVehiclePlanById(planPrice.getVehiclePlanId());
                    booking.setVehiclePlan(vehiclePlan);

                    DistanceRange distanceRange = distanceRangeDAO.getDistanceRangeById(planPrice.getDistanceRangeId());
                    booking.setDistanceRange(distanceRange);
                }

                // Fetch the vehicle details using VehicleDAO
                VehicleDAO vehicleDAO = new VehicleDAO(DBConnection.getInstance());
                Vehicle vehicle = vehicleDAO.findVehicalByID(vehicleId);
                booking.setVehicle(vehicle);

                // Set specific vehicle types if applicable
                if (vehicle instanceof PremiumVehicle premiumVehicle) {
                    booking.setPremiumVehicle(premiumVehicle);
                } else if (vehicle instanceof VIPVehicle vipVehicle) {
                    booking.setVIPVehicle(vipVehicle);
                }

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

            String sql = "UPDATE booking SET customer_id = ?, pickup_location = ?, drop_location = ?, booking_type = ?, vehicle_id = ?, driver_id = ?, initial_km = ?, final_km = ?, pickup_time = ?, return_time = ?, days_needed = ?, tax_id = ?,employee_id = ?, package_id = ? WHERE order_no = ?";
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
                statement.setInt(13,booking.getTax_id());
                statement.setInt (14,booking.getEmployee_id());
                statement.setInt(15,booking.getPackage_id());

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