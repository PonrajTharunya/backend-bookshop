package org.project.mega_city_cab_service_app.service.TaxService;

import org.project.mega_city_cab_service_app.dao.TaxDAO;
import org.project.mega_city_cab_service_app.model.tax.Tax;

import java.util.List;

public class TaxService {
    private final TaxDAO taxDAO;

    // Constructor to inject the TaxDAO dependency
    public TaxService(TaxDAO taxDAO) {
        this.taxDAO = taxDAO;
    }

    /**
     * Add a new tax.
     *
     * @param tax The tax object to be added.
     * @return true if the tax was successfully added, false otherwise.
     */
    public boolean addTax(Tax tax) {
        return taxDAO.addTax(tax);
    }

    /**
     * Retrieve all taxes.
     *
     * @return A list of all taxes in the system.
     */
    public List<Tax> getAllTaxes() {
        return taxDAO.getAllTaxes();
    }

    /**
     * Retrieve a tax by its ID.
     *
     * @param taxId The ID of the tax to retrieve.
     * @return The tax object if found, null otherwise.
     */
    public Tax getTaxById(int taxId) {
        return taxDAO.findTaxById(taxId);
    }

    /**
     * Update an existing tax.
     *
     * @param tax The updated tax object.
     * @return true if the tax was successfully updated, false otherwise.
     */
    public boolean updateTax(Tax tax) {
        return taxDAO.updateTax(tax);
    }

    /**
     * Delete a tax by its ID.
     *
     * @param taxId The ID of the tax to delete.
     * @return true if the tax was successfully deleted, false otherwise.
     */
    public boolean deleteTax(int taxId) {
        return taxDAO.deleteTax(taxId);
    }
}