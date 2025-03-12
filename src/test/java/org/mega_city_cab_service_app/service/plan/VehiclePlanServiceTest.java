package org.mega_city_cab_service_app.service.plan;

import org.junit.jupiter.api.*;
import org.project.mega_city_cab_service_app.dao.VehiclePlan.VehiclePlanDAO;
import org.project.mega_city_cab_service_app.model.Plan.VehiclePlan;
import org.project.mega_city_cab_service_app.service.planService.VehiclePlanService;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehiclePlanServiceTest {

    private VehiclePlanDAO vehiclePlanDAO;
    private VehiclePlanService vehiclePlanService;

    @BeforeAll
    public void setUp() {
        vehiclePlanDAO = new VehiclePlanDAO();
        vehiclePlanService = new VehiclePlanService(vehiclePlanDAO);
        clearDatabase();
    }

    @AfterAll
    public void tearDown() {
        clearDatabase();
    }

    @BeforeEach
    public void setUpEach() {
        clearDatabase();
    }

    @AfterEach
    public void tearDownEach() {
        clearDatabase();
    }

    private void clearDatabase() {
        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("SET FOREIGN_KEY_CHECKS = 0");
            statement.execute("DELETE FROM vehicle_plan");
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddVehiclePlan() {
        boolean result = vehiclePlanService.addVehiclePlan("Basic Plan");
        assertTrue(result);

        VehiclePlan fetchedPlan = vehiclePlanService.getVehiclePlanById(1);
        assertNotNull(fetchedPlan);
        assertEquals("Basic Plan", fetchedPlan.getPlanName());
    }

    @Test
    public void testGetAllVehiclePlans() {
        vehiclePlanService.addVehiclePlan("Basic Plan");
        vehiclePlanService.addVehiclePlan("Premium Plan");

        List<VehiclePlan> plans = vehiclePlanService.getAllVehiclePlans();
        assertNotNull(plans);
        assertEquals(2, plans.size());
        assertEquals("Basic Plan", plans.get(0).getPlanName());
        assertEquals("Premium Plan", plans.get(1).getPlanName());
    }

    @Test
    public void testGetVehiclePlanById() {
        vehiclePlanService.addVehiclePlan("Basic Plan");

        VehiclePlan fetchedPlan = vehiclePlanService.getVehiclePlanById(1);
        assertNotNull(fetchedPlan);
        assertEquals("Basic Plan", fetchedPlan.getPlanName());
    }

    @Test
    public void testUpdateVehiclePlan() {
        vehiclePlanService.addVehiclePlan("Basic Plan");

        boolean result = vehiclePlanService.updateVehiclePlan(1, "Updated Plan");
        assertTrue(result);

        VehiclePlan updatedPlan = vehiclePlanService.getVehiclePlanById(1);
        assertNotNull(updatedPlan);
        assertEquals("Updated Plan", updatedPlan.getPlanName());
    }

    @Test
    public void testDeleteVehiclePlan() {
        vehiclePlanService.addVehiclePlan("Basic Plan");

        boolean result = vehiclePlanService.deleteVehiclePlan(1);
        assertTrue(result);

        VehiclePlan deletedPlan = vehiclePlanService.getVehiclePlanById(1);
        assertNull(deletedPlan);
    }
}