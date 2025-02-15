package org.project.mega_city_cab_service_app.repository.Person;

import org.project.mega_city_cab_service_app.model.person.Driver;
import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;

public class DriverRepository implements PersonRepository {
    private final DBConnection dbConnection;

    public DriverRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean save(Person person) {
        if (!(person instanceof Driver driver)) {
            throw new IllegalArgumentException("Invalid person type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Save to `person` table
            String personSql = "INSERT INTO person (type, name, address, mobile, username, password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement personStatement = connection.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS)) {
                personStatement.setString(1, person.getType());
                personStatement.setString(2, person.getName());
                personStatement.setString(3, person.getAddress());
                personStatement.setString(4, person.getMobile());
                personStatement.setString(5, person.getUsername());
                personStatement.setString(6, person.getPassword());
                personStatement.executeUpdate();

                ResultSet generatedKeys = personStatement.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new SQLException("Failed to retrieve generated keys.");
                }
                int personId = generatedKeys.getInt(1);

                // Save to `employee` table
                String employeeSql = "INSERT INTO employee (id, salary, experience) VALUES (?, ?, ?)";
                try (PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {
                    employeeStatement.setInt(1, personId);
                    employeeStatement.setDouble(2, driver.getSalary());
                    employeeStatement.setInt(3, driver.getExperience());
                    employeeStatement.executeUpdate();
                }

                // Save to `driver` table
                String driverSql = "INSERT INTO driver (id, license_number, license_type) VALUES (?, ?, ?)";
                try (PreparedStatement driverStatement = connection.prepareStatement(driverSql)) {
                    driverStatement.setInt(1, personId);
                    driverStatement.setString(2, driver.getLicenseNumber());
                    driverStatement.setString(3, driver.getLicenseType());
                    System.out.println(driver.getLicenseType());
                    driverStatement.executeUpdate();
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

    @Override
    public Person findByMobile(String mobile) {
        String personSql = "SELECT * FROM person WHERE mobile = ?";
        String employeeSql = "SELECT * FROM employee WHERE id = ?";
        String driverSql = "SELECT * FROM driver WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement personStatement = connection.prepareStatement(personSql);
             PreparedStatement employeeStatement = connection.prepareStatement(employeeSql);
             PreparedStatement driverStatement = connection.prepareStatement(driverSql)) {

            personStatement.setString(1, mobile);
            ResultSet personResultSet = personStatement.executeQuery();

            if (!personResultSet.next()) {
                return null; // No person found
            }

            int id = personResultSet.getInt("id");
            String name = personResultSet.getString("name");
            String address = personResultSet.getString("address");
            String username = personResultSet.getString("username");
            String password = personResultSet.getString("password");

            employeeStatement.setInt(1, id);
            ResultSet employeeResultSet = employeeStatement.executeQuery();

            if (!employeeResultSet.next()) {
                return null; // No employee found
            }

            double salary = employeeResultSet.getDouble("salary");
            int experience = employeeResultSet.getInt("experience");


            driverStatement.setInt(1, id);
            ResultSet driverResultSet = driverStatement.executeQuery();

            if (!driverResultSet.next()) {
                return null; // No driver found
            }

            String licenseNumber = driverResultSet.getString("license_number");
            String licenseType = driverResultSet.getString("license_type");

            return new Driver(name, address, mobile, username,password,salary, experience, licenseNumber, licenseType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(String originalMobile, Person updatedPerson) {
        if (!(updatedPerson instanceof Driver driver)) {
            throw new IllegalArgumentException("Invalid person type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Update `person` table
            String personSql = "UPDATE person SET name = ?, address = ?, mobile = ? WHERE mobile = ?";
            try (PreparedStatement personStatement = connection.prepareStatement(personSql)) {
                personStatement.setString(1, updatedPerson.getName());
                personStatement.setString(2, updatedPerson.getAddress());
                personStatement.setString(3, updatedPerson.getMobile());
                personStatement.setString(4, originalMobile);
                personStatement.executeUpdate();
            }

            // Retrieve ID of the person
            String idSql = "SELECT id FROM person WHERE mobile = ?";
            int personId;
            try (PreparedStatement idStatement = connection.prepareStatement(idSql)) {
                idStatement.setString(1, updatedPerson.getMobile());
                ResultSet idResultSet = idStatement.executeQuery();
                if (!idResultSet.next()) {
                    throw new SQLException("Failed to retrieve person ID.");
                }
                personId = idResultSet.getInt("id");
            }

            // Update `employee` table
            String employeeSql = "UPDATE employee SET salary = ?, experience = ? WHERE id = ?";
            try (PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {
                employeeStatement.setDouble(1, driver.getSalary());
                employeeStatement.setInt(2, driver.getExperience());
                employeeStatement.setInt(3, personId);
                employeeStatement.executeUpdate();
            }

            // Update `driver` table
            String driverSql = "UPDATE driver SET license_number = ?, vehicle_type = ? WHERE id = ?";
            try (PreparedStatement driverStatement = connection.prepareStatement(driverSql)) {
                driverStatement.setString(1, driver.getLicenseNumber());
                driverStatement.setString(2, driver.getLicenseType());
                driverStatement.setInt(3, personId);
                driverStatement.executeUpdate();
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

    @Override
    public boolean delete(String mobile) {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Retrieve ID of the person
            String idSql = "SELECT id FROM person WHERE mobile = ?";
            int personId;
            try (PreparedStatement idStatement = connection.prepareStatement(idSql)) {
                idStatement.setString(1, mobile);
                ResultSet idResultSet = idStatement.executeQuery();
                if (!idResultSet.next()) {
                    throw new SQLException("Failed to retrieve person ID.");
                }
                personId = idResultSet.getInt("id");
            }

            // Delete from `driver` table
            String driverSql = "DELETE FROM driver WHERE id = ?";
            try (PreparedStatement driverStatement = connection.prepareStatement(driverSql)) {
                driverStatement.setInt(1, personId);
                driverStatement.executeUpdate();
            }

            // Delete from `employee` table
            String employeeSql = "DELETE FROM employee WHERE id = ?";
            try (PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {
                employeeStatement.setInt(1, personId);
                employeeStatement.executeUpdate();
            }

            // Delete from `person` table
            String personSql = "DELETE FROM person WHERE mobile = ?";
            try (PreparedStatement personStatement = connection.prepareStatement(personSql)) {
                personStatement.setString(1, mobile);
                personStatement.executeUpdate();
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