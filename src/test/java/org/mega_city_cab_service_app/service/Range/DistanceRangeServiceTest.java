package org.mega_city_cab_service_app.service.Range;

import org.junit.jupiter.api.*;
import org.project.mega_city_cab_service_app.dao.DistanceRange.DistanceRangeDAO;
import org.project.mega_city_cab_service_app.model.Range.DistanceRange;
import org.project.mega_city_cab_service_app.service.distanceRange.DistanceRangeService;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DistanceRangeServiceTest {

    private DistanceRangeDAO distanceRangeDAO;
    private DistanceRangeService distanceRangeService;

    @BeforeAll
    public void setUp() {
        distanceRangeDAO = new DistanceRangeDAO();
        distanceRangeService = new DistanceRangeService(distanceRangeDAO);
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
            statement.execute("DELETE FROM plan_price");
            statement.execute("DELETE FROM distance_ranges");
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddDistanceRange() {
        DistanceRange distanceRange = new DistanceRange(10, 20);
        boolean result = distanceRangeService.addDistanceRange(distanceRange);
        assertTrue(result);
        assertNotNull(distanceRange.getId());
    }

    @Test
    public void testGetAllDistanceRanges() {
        DistanceRange range1 = new DistanceRange(10, 20);
        DistanceRange range2 = new DistanceRange(30, 40);
        distanceRangeService.addDistanceRange(range1);
        distanceRangeService.addDistanceRange(range2);

        List<DistanceRange> ranges = distanceRangeService.getAllDistanceRanges();
        assertNotNull(ranges);
        assertEquals(2, ranges.size());
        assertEquals(10, ranges.get(0).getMinDistance());
        assertEquals(20, ranges.get(0).getMaxDistance());
        assertEquals(30, ranges.get(1).getMinDistance());
        assertEquals(40, ranges.get(1).getMaxDistance());
    }

    @Test
    public void testGetDistanceRangeById() {
        DistanceRange range = new DistanceRange(10, 20);
        distanceRangeService.addDistanceRange(range);

        DistanceRange fetchedRange = distanceRangeService.getDistanceRangeById(range.getId());
        assertNotNull(fetchedRange);
        assertEquals(10, fetchedRange.getMinDistance());
        assertEquals(20, fetchedRange.getMaxDistance());
    }

    @Test
    public void testUpdateDistanceRange() {
        DistanceRange range = new DistanceRange(10, 20);
        distanceRangeService.addDistanceRange(range);

        range.setMinDistance(15);
        range.setMaxDistance(25);
        boolean result = distanceRangeService.updateDistanceRange(range);

        assertTrue(result);

        DistanceRange updatedRange = distanceRangeService.getDistanceRangeById(range.getId());
        assertNotNull(updatedRange);
        assertEquals(15, updatedRange.getMinDistance());
        assertEquals(25, updatedRange.getMaxDistance());
    }

    @Test
    public void testDeleteDistanceRange() {
        DistanceRange range = new DistanceRange(10, 20);
        distanceRangeService.addDistanceRange(range);

        boolean result = distanceRangeService.deleteDistanceRange(range.getId());
        assertTrue(result);

        DistanceRange deletedRange = distanceRangeService.getDistanceRangeById(range.getId());
        assertNull(deletedRange);
    }
}