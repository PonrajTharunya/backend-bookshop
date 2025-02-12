package org.project.mega_city_cab_service_app.repository.Person;

import org.project.mega_city_cab_service_app.model.person.Employee;
import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;

public class EmployeeRepository implements PersonRepository {
    private final DBConnection dbConnection;

    public EmployeeRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean save(Person person) {
        if (!(person instanceof Employee employee)) {
            throw new IllegalArgumentException("Invalid person type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Save to `person` table
            String personSql = "INSERT INTO person (type, name, address, mobile,  username , password) VALUES (?, ?, ?, ?, ?, ?)";
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
                    employeeStatement.setDouble(2, employee.getSalary());
                    employeeStatement.setInt(3, employee.getExperience());
                    employeeStatement.executeUpdate();
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

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement personStatement = connection.prepareStatement(personSql);
             PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {

            personStatement.setString(1, mobile);
            ResultSet personResultSet = personStatement.executeQuery();

            if (!personResultSet.next()) {
                return null; // No person found
            }

            int id = personResultSet.getInt("id");
            String name = personResultSet.getString("name");
            String address = personResultSet.getString("address");
            String  username= personResultSet.getString("username");
            String password = personResultSet.getString("password");

            employeeStatement.setInt(1, id);
            ResultSet employeeResultSet = employeeStatement.executeQuery();

            if (!employeeResultSet.next()) {
                return null; // No employee found
            }

            double salary = employeeResultSet.getDouble("salary");
            int experience = employeeResultSet.getInt("experience");
            return new Employee(name, address, mobile, username ,password, salary, experience);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(String originalMobile, Person updatedPerson) {
        if (!(updatedPerson instanceof Employee employee)) {
            throw new IllegalArgumentException("Invalid person type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Update `person` table
            String personSql = "UPDATE person SET name = ?, address = ?, mobile = ?,  username = ?, password = ? WHERE mobile = ?";
            try (PreparedStatement personStatement = connection.prepareStatement(personSql)) {
                personStatement.setString(1, updatedPerson.getName());
                personStatement.setString(2, updatedPerson.getAddress());
                personStatement.setString(3, updatedPerson.getMobile());
                personStatement.setString(4, originalMobile);
                personStatement.setString(5, updatedPerson.getUsername());
                personStatement.setString(6, updatedPerson.getPassword());
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
                employeeStatement.setDouble(1, employee.getSalary());
                employeeStatement.setInt(2, employee.getExperience());
                employeeStatement.setInt(3, personId);
                employeeStatement.executeUpdate();
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