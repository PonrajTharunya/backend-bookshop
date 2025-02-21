package org.project.mega_city_cab_service_app.dao;

import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.repository.Vehicle.*;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleDAO {
    private final Map<String, VehicleRepository> repositories;

    public VehicleDAO(DBConnection dbConnection) {
        this.repositories = new HashMap<>();
        repositories.put("Regular", new RegularVehicleRepository(dbConnection));
        repositories.put("Premium", new PremiumVehicleRepository(dbConnection));
        repositories.put("VIP", new VIPVehicleRepository(dbConnection));
    }

    public boolean addVehicle(Vehicle vehicle) {
        VehicleRepository repository = repositories.get(vehicle.getType());
        if (repository == null) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + vehicle.getType());
        }
        return repository.save(vehicle);
    }

    public Vehicle findVehicalByID(int vehicleId) {
        String type = getTypeFromDatabase(vehicleId);
        VehicleRepository repository = repositories.get(type);
        if (repository == null) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + type);
        }
        return repository.findByID(vehicleId);
    }

    public boolean updateVehicle(Vehicle vehicle) {
        VehicleRepository repository = repositories.get(vehicle.getType());
        if (repository == null) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + vehicle.getType());
        }
        return repository.update(vehicle);
    }

    public boolean deleteVehicle(int vehicleId) {
        String type = getTypeFromDatabase(vehicleId);
        VehicleRepository repository = repositories.get(type);
        if (repository == null) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + type);
        }
        return repository.delete(vehicleId);
    }

    private String getTypeFromDatabase(int vehicleId) {
        String sql = "SELECT type FROM vehicle WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Vehicle not found with ID: " + vehicleId);
    }
    public List<Vehicle> findVehiclesByType(String type) {
        VehicleRepository repository = repositories.get(type);
        if (repository == null) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + type);
        }
        return repository.findAll();
    }
}