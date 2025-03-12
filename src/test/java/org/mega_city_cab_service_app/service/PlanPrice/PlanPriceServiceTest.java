package org.mega_city_cab_service_app.service.PlanPrice;

import org.junit.jupiter.api.*;
import org.project.mega_city_cab_service_app.dao.PlanPrice.PlanPriceDAO;
import org.project.mega_city_cab_service_app.model.PlanPrice.PlanPrice;
import org.project.mega_city_cab_service_app.service.PlanPriceService.PlanPriceService;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlanPriceServiceTest {

    private PlanPriceDAO planPriceDAO;
    private PlanPriceService planPriceService;

    @BeforeAll
    public void setUp() {
        planPriceDAO = new PlanPriceDAO();
        planPriceService = new PlanPriceService(planPriceDAO);
        clearDatabase();
        populateDistanceRanges(); // Populate required test data
    }

    @AfterAll
    public void tearDown() {
        clearDatabase();
    }

    @BeforeEach
    public void setUpEach() {
        clearDatabase();
        populateDistanceRanges(); // Ensure distance ranges are always populated
    }

    @AfterEach
    public void tearDownEach() {
        clearDatabase();
    }

    private void clearDatabase() {
        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("SET FOREIGN_KEY_CHECKS = 0");
            statement.execute("DELETE FROM plan_price");
            statement.execute("DELETE FROM distance_ranges");
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateDistanceRanges() {
        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            // Insert valid data into the distance_ranges table
            statement.execute("INSERT INTO distance_ranges (id, min_distance, max_distance) VALUES (1, 0, 10)");
            statement.execute("INSERT INTO distance_ranges (id, min_distance, max_distance) VALUES (2, 11, 20)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddPlanPrice() {
        // Create a valid PlanPrice object with a valid distance_range_id
        PlanPrice planPrice = new PlanPrice(1L, 100.0, 10.0, 5.0, 1);

        // Attempt to add the PlanPrice to the database
        boolean result = planPriceService.addPlanPrice(planPrice);

        // Assert that the insertion was successful
        assertTrue(result, "The plan price should have been added successfully.");

        // Optionally, verify that the ID was generated (if applicable)
        assertNotNull(planPrice.getId(), "The plan price ID should not be null after insertion.");
    }
}