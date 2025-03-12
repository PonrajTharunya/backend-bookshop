package org.project.mega_city_cab_service_app.service.VehicleService;

import org.junit.jupiter.api.*;
import org.project.mega_city_cab_service_app.dao.VehicleDAO;
import org.project.mega_city_cab_service_app.model.Parent.Vehicle;
import org.project.mega_city_cab_service_app.model.vehical.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehicleServiceTest {

    private VehicleDAO vehicleDAO;
    private VehicleService vehicleService;

    @BeforeAll
    public void setUp() {
        // Use a mock implementation of VehicleDAO for testing
        vehicleDAO = new MockVehicleDAO();
        vehicleService = new VehicleService(vehicleDAO);
    }

    @Test
    public void testRegisterVehicle_RegularVehicle() {
        // Arrange
        RegularVehicle vehicle = new RegularVehicle(
                "Toyota Corolla", "2023", "Red", 2023, 12345, 5
        );

        // Act
        boolean result = vehicleService.registerVehicle(vehicle);

        // Assert
        assertTrue(result, "Regular vehicle registration should succeed.");
    }

    @Test
    public void testRegisterVehicle_PremiumVehicle() {
        // Arrange
        PremiumVehicle vehicle = new PremiumVehicle(
                "Mercedes-Benz", "S-Class", "Black", 2023, 67890, 4, true
        );

        // Act
        boolean result = vehicleService.registerVehicle(vehicle);

        // Assert
        assertTrue(result, "Premium vehicle registration should succeed.");
    }

    @Test
    public void testRegisterVehicle_VIPVehicle() {
        // Arrange
        VIPVehicle vehicle = new VIPVehicle(
                "Rolls-Royce", "Phantom", "White", 2023, 54321, 4, true
        );

        // Act
        boolean result = vehicleService.registerVehicle(vehicle);

        // Assert
        assertTrue(result, "VIP vehicle registration should succeed.");
    }
}

// Mock implementation of VehicleDAO for testing
class MockVehicleDAO extends VehicleDAO {

    public MockVehicleDAO() {
        super(null); // Pass null since DBConnection is not used
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) {
        // Simulate successful addition of a vehicle
        return true;
    }

    @Override
    public Vehicle findVehicalByID(int vehicleId) {
        // Not needed for this test
        return null;
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        // Not needed for this test
        return false;
    }

    @Override
    public boolean deleteVehicle(int vehicleId) {
        // Not needed for this test
        return false;
    }

    @Override
    public List<Vehicle> findVehiclesByType(String type) {
        // Not needed for this test
        return null;
    }
}