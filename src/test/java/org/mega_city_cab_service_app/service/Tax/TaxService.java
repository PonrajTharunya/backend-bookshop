package org.mega_city_cab_service_app.service.Tax;

import org.junit.jupiter.api.Test;
import org.project.mega_city_cab_service_app.dao.TaxDAO;
import org.project.mega_city_cab_service_app.model.tax.Tax;
import org.project.mega_city_cab_service_app.service.TaxService.TaxService;

import static org.junit.jupiter.api.Assertions.*;

class TaxServiceTest {

    @Test
    void testAddTax() {
        // Create a real TaxDAO instance (ensure DBConnection is configured)
        TaxDAO taxDAO = new TaxDAO();

        // Create TaxService with the real DAO
        TaxService taxService = new TaxService(taxDAO);

        // Test addTax method
        Tax tax = new Tax("GST", 18.0, true);
        boolean result = taxService.addTax(tax);

        assertTrue(result);
    }

    @Test
    void testGetAllTaxes() {
        // Create a real TaxDAO instance
        TaxDAO taxDAO = new TaxDAO();

        // Create TaxService with the real DAO
        TaxService taxService = new TaxService(taxDAO);

        // Add a tax to the database
        Tax tax = new Tax("VAT", 12.0, false);
        taxService.addTax(tax);

        // Test getAllTaxes method
        var taxes = taxService.getAllTaxes();
        assertFalse(taxes.isEmpty());
    }
}