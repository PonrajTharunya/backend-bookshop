package org.project.mega_city_cab_service_app.dao;

import org.project.mega_city_cab_service_app.model.tax.Tax;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaxDAO {

    // Save a new tax
    public boolean addTax(Tax tax) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "INSERT INTO tax (description, tax_rate, status) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, tax.getDescription());
                statement.setDouble(2, tax.getTaxRate());
                statement.setBoolean(3, tax.isStatus());

                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    tax.setTaxId(generatedKeys.getInt(1));
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

    // Retrieve all taxes
    public List<Tax> getAllTaxes() {
        List<Tax> taxes = new ArrayList<>();
        String sql = "SELECT * FROM tax";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int taxId = resultSet.getInt("tax_id");
                String description = resultSet.getString("description");
                double taxRate = resultSet.getDouble("tax_rate");
                boolean status = resultSet.getBoolean("status");

                Tax tax = new Tax(description, taxRate, status);
                tax.setTaxId(taxId);
                taxes.add(tax);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taxes;
    }

    // Retrieve a tax by its ID
    public Tax findTaxById(int taxId) {
        String sql = "SELECT * FROM tax WHERE tax_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, taxId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String description = resultSet.getString("description");
                double taxRate = resultSet.getDouble("tax_rate");
                boolean status = resultSet.getBoolean("status");

                Tax tax = new Tax(description, taxRate, status);
                tax.setTaxId(taxId);
                return tax;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing tax
    public boolean updateTax(Tax tax) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String sql = "UPDATE tax SET description = ?, tax_rate = ?, status = ? WHERE tax_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, tax.getDescription());
                statement.setDouble(2, tax.getTaxRate());
                statement.setBoolean(3, tax.isStatus());
                statement.setInt(4, tax.getTaxId());

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

    // Delete a tax by its ID
    public boolean deleteTax(int taxId) {
        String sql = "DELETE FROM tax WHERE tax_id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, taxId);
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