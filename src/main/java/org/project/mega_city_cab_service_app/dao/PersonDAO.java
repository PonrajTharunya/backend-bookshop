//package org.project.mega_city_cab_service_app.dao;
//
//import org.project.mega_city_cab_service_app.model.Parent.Person;
//import org.project.mega_city_cab_service_app.model.person.Employee;
//
//import java.util.HashMap;
//import java.util.Map;
//
////responsible for managing data access operations
//public class PersonDAO {
//    private final Map<String, Person> personMap = new HashMap<>();
//
//    public boolean savePerson(Person person) {
//        System.out.println("Saving Person Type: " + person.getType());
//        System.out.println("Name: " + person.getName());
//        System.out.println("Address: " + person.getAddress());
//        System.out.println("Mobile: " + person.getMobile());
//
//        if (person instanceof Employee) {
//            Employee emp = (Employee) person;
//            System.out.println("Salary: " + emp.getSalary());
//            System.out.println("Experience: " + emp.getExperience());
//        }
//
//        personMap.put(person.getMobile(), person);
//        return true;
//    }
//    public Person findPersonByMobile(String mobile) {
//        return personMap.get(mobile);
//    }
////    public boolean updatePerson(Person person) {
////        if (personMap.containsKey(person.getMobile())) {
////            personMap.put(person.getMobile(), person);
////            System.out.println("Updated Person: " + person.getName());
////            return true;
////        }
////        System.out.println("Person not found for update.");
////        return false;
////    }
//
//    public boolean updatePerson(String originalMobile, Person updatedPerson) {
//        // Check if the person exists
//        if (!personMap.containsKey(originalMobile)) {
//            System.out.println("Person not found for update.");
//            return false;
//        }
//
//        // If the mobile number is being updated, remove the old entry
//        if (!originalMobile.equals(updatedPerson.getMobile())) {
//            personMap.remove(originalMobile);
//        }
//
//        // Save the updated person
//        personMap.put(updatedPerson.getMobile(), updatedPerson);
//        System.out.println("Updated Person: " + updatedPerson.getName());
//        return true;
//    }
//
//    public boolean deletePerson(String mobile) {
//        Person person = personMap.remove(mobile);
//        if (person != null) {
//            System.out.println("Deleted Person: " + person.getName());
//            return true;
//        }
//        System.out.println("Person not found for deletion.");
//        return false;
//    }
//
//}
/// /////////////////////////////////////////
//package org.project.mega_city_cab_service_app.dao;
//
//import org.project.mega_city_cab_service_app.model.Parent.Person;
//import org.project.mega_city_cab_service_app.model.person.Customer;
//import org.project.mega_city_cab_service_app.model.person.Employee;
//import org.project.mega_city_cab_service_app.util.DBConnection;
//
//import java.sql.*;
//
//public class PersonDAO {
//    private final DBConnection dbConnection;
//
//    public PersonDAO() {
//        this.dbConnection = DBConnection.getInstance();
//    }
//
//    // Save a Person object to the database
//    public boolean savePerson(Person person) {
//        Connection connection = null;
//        try {
//            connection = dbConnection.getConnection();
//            connection.setAutoCommit(false); // Start transaction
//
//            // Insert into the `person` table
//            String personSql = "INSERT INTO person (type, name, address, mobile) VALUES (?, ?, ?, ?)";
//            try (PreparedStatement personStatement = connection.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS)) {
//                personStatement.setString(1, person.getType());
//                personStatement.setString(2, person.getName());
//                personStatement.setString(3, person.getAddress());
//                personStatement.setString(4, person.getMobile());
//
//                int rowsAffected = personStatement.executeUpdate();
//                if (rowsAffected == 0) {
//                    throw new SQLException("Failed to insert into person table.");
//                }
//
//                // Retrieve the generated ID
//                ResultSet generatedKeys = personStatement.getGeneratedKeys();
//                if (!generatedKeys.next()) {
//                    throw new SQLException("Failed to retrieve generated ID.");
//                }
//                int personId = generatedKeys.getInt(1);
//
//                // Insert into the type-specific table
//                if (person instanceof Customer customer) {
//                    String customerSql = "INSERT INTO customer (id, rating, description) VALUES (?, ?, ?)";
//                    try (PreparedStatement customerStatement = connection.prepareStatement(customerSql)) {
//                        customerStatement.setInt(1, personId);
//                        customerStatement.setInt(2, customer.getRating());
//                        customerStatement.setString(3, customer.getDescription());
//                        customerStatement.executeUpdate();
//                    }
//                } else if (person instanceof Employee employee) {
//                    String employeeSql = "INSERT INTO employee (id, salary, experience) VALUES (?, ?, ?)";
//                    try (PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {
//                        employeeStatement.setInt(1, personId);
//                        employeeStatement.setDouble(2, employee.getSalary());
//                        employeeStatement.setInt(3, employee.getExperience());
//                        employeeStatement.executeUpdate();
//                    }
//                }
//
//                connection.commit(); // Commit transaction
//                return true;
//            }
//        } catch (SQLException e) {
//            if (connection != null) {
//                try {
//                    connection.rollback(); // Rollback transaction on error
//                } catch (SQLException rollbackEx) {
//                    rollbackEx.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//            return false;
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.setAutoCommit(true); // Restore auto-commit mode
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
//
//    // Retrieve a Person object by mobile number
//    public Person findPersonByMobile(String mobile) {
//        String personSql = "SELECT * FROM person WHERE mobile = ?";
//        String customerSql = "SELECT * FROM customer WHERE id = ?";
//        String employeeSql = "SELECT * FROM employee WHERE id = ?";
//
//        try (Connection connection = dbConnection.getConnection();
//             PreparedStatement personStatement = connection.prepareStatement(personSql);
//             PreparedStatement customerStatement = connection.prepareStatement(customerSql);
//             PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {
//
//            // Retrieve from the `person` table
//            personStatement.setString(1, mobile);
//            ResultSet personResultSet = personStatement.executeQuery();
//
//            if (!personResultSet.next()) {
//                return null; // No person found
//            }
//
//            int id = personResultSet.getInt("id");
//            String type = personResultSet.getString("type");
//            String name = personResultSet.getString("name");
//            String address = personResultSet.getString("address");
//
//            // Retrieve from the type-specific table
//            if ("CUSTOMER".equalsIgnoreCase(type)) {
//                customerStatement.setInt(1, id);
//                ResultSet customerResultSet = customerStatement.executeQuery();
//                if (customerResultSet.next()) {
//                    int rating = customerResultSet.getInt("rating");
//                    String description = customerResultSet.getString("description");
//                    return new Customer(name, address, mobile, rating, description);
//                }
//            } else if ("EMPLOYEE".equalsIgnoreCase(type)) {
//                employeeStatement.setInt(1, id);
//                ResultSet employeeResultSet = employeeStatement.executeQuery();
//                if (employeeResultSet.next()) {
//                    double salary = employeeResultSet.getDouble("salary");
//                    int experience = employeeResultSet.getInt("experience");
//                    return new Employee(name, address, mobile, salary, experience);
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    // Update a Person object in the database
//    public boolean updatePerson(String originalMobile, Person updatedPerson) {
//        Connection connection = null;
//        try {
//            connection = dbConnection.getConnection();
//            connection.setAutoCommit(false); // Start transaction
//
//            // Update the `person` table
//            String personSql = "UPDATE person SET type = ?, name = ?, address = ?, mobile = ? WHERE mobile = ?";
//            try (PreparedStatement personStatement = connection.prepareStatement(personSql)) {
//                personStatement.setString(1, updatedPerson.getType());
//                personStatement.setString(2, updatedPerson.getName());
//                personStatement.setString(3, updatedPerson.getAddress());
//                personStatement.setString(4, updatedPerson.getMobile());
//                personStatement.setString(5, originalMobile);
//
//                int rowsAffected = personStatement.executeUpdate();
//                if (rowsAffected == 0) {
//                    throw new SQLException("Failed to update person table.");
//                }
//            }
//
//            // Retrieve the ID of the person being updated
//            String idSql = "SELECT id FROM person WHERE mobile = ?";
//            int personId;
//            try (PreparedStatement idStatement = connection.prepareStatement(idSql)) {
//                idStatement.setString(1, updatedPerson.getMobile());
//                ResultSet idResultSet = idStatement.executeQuery();
//                if (!idResultSet.next()) {
//                    throw new SQLException("Failed to retrieve person ID.");
//                }
//                personId = idResultSet.getInt("id");
//            }
//
//            // Update the type-specific table
//            if (updatedPerson instanceof Customer customer) {
//                String customerSql = "UPDATE customer SET rating = ?, description = ? WHERE id = ?";
//                try (PreparedStatement customerStatement = connection.prepareStatement(customerSql)) {
//                    customerStatement.setInt(1, customer.getRating());
//                    customerStatement.setString(2, customer.getDescription());
//                    customerStatement.setInt(3, personId);
//                    customerStatement.executeUpdate();
//                }
//            } else if (updatedPerson instanceof Employee employee) {
//                String employeeSql = "UPDATE employee SET salary = ?, experience = ? WHERE id = ?";
//                try (PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {
//                    employeeStatement.setDouble(1, employee.getSalary());
//                    employeeStatement.setInt(2, employee.getExperience());
//                    employeeStatement.setInt(3, personId);
//                    employeeStatement.executeUpdate();
//                }
//            }
//
//            connection.commit(); // Commit transaction
//            return true;
//        } catch (SQLException e) {
//            if (connection != null) {
//                try {
//                    connection.rollback(); // Rollback transaction on error
//                } catch (SQLException rollbackEx) {
//                    rollbackEx.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//            return false;
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.setAutoCommit(true); // Restore auto-commit mode
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
//
//    // Delete a Person object from the database
//    public boolean deletePerson(String mobile) {
//        Connection connection = null;
//        try {
//            connection = dbConnection.getConnection();
//            connection.setAutoCommit(false); // Start transaction
//
//            // Retrieve the ID of the person being deleted
//            String idSql = "SELECT id FROM person WHERE mobile = ?";
//            int personId;
//            try (PreparedStatement idStatement = connection.prepareStatement(idSql)) {
//                idStatement.setString(1, mobile);
//                ResultSet idResultSet = idStatement.executeQuery();
//                if (!idResultSet.next()) {
//                    throw new SQLException("Failed to retrieve person ID.");
//                }
//                personId = idResultSet.getInt("id");
//            }
//
//            // Delete from the type-specific table
//            String typeSql = "SELECT type FROM person WHERE mobile = ?";
//            String type;
//            try (PreparedStatement typeStatement = connection.prepareStatement(typeSql)) {
//                typeStatement.setString(1, mobile);
//                ResultSet typeResultSet = typeStatement.executeQuery();
//                if (!typeResultSet.next()) {
//                    throw new SQLException("Failed to retrieve person type.");
//                }
//                type = typeResultSet.getString("type");
//            }
//
//            if ("CUSTOMER".equalsIgnoreCase(type)) {
//                String customerSql = "DELETE FROM customer WHERE id = ?";
//                try (PreparedStatement customerStatement = connection.prepareStatement(customerSql)) {
//                    customerStatement.setInt(1, personId);
//                    customerStatement.executeUpdate();
//                }
//            } else if ("EMPLOYEE".equalsIgnoreCase(type)) {
//                String employeeSql = "DELETE FROM employee WHERE id = ?";
//                try (PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {
//                    employeeStatement.setInt(1, personId);
//                    employeeStatement.executeUpdate();
//                }
//            }
//
//            // Delete from the `person` table
//            String personSql = "DELETE FROM person WHERE mobile = ?";
//            try (PreparedStatement personStatement = connection.prepareStatement(personSql)) {
//                personStatement.setString(1, mobile);
//                int rowsAffected = personStatement.executeUpdate();
//                if (rowsAffected == 0) {
//                    throw new SQLException("Failed to delete from person table.");
//                }
//            }
//
//            connection.commit(); // Commit transaction
//            return true;
//        } catch (SQLException e) {
//            if (connection != null) {
//                try {
//                    connection.rollback(); // Rollback transaction on error
//                } catch (SQLException rollbackEx) {
//                    rollbackEx.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//            return false;
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.setAutoCommit(true); // Restore auto-commit mode
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }
//}

package org.project.mega_city_cab_service_app.dao;
import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.repository.Person.*;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PersonDAO {
    private final Map<String, PersonRepository> repositories;

    public PersonDAO(DBConnection dbConnection) {
        this.repositories = new HashMap<>();
        repositories.put("ADMIN", new AdminRepository(dbConnection));
        repositories.put("CUSTOMER", new CustomerRepository(dbConnection));
        repositories.put("EMPLOYEE", new EmployeeRepository(dbConnection));
        repositories.put("DRIVER",new DriverRepository(dbConnection));
        // Add more repositories as needed
    }

    public boolean savePerson(Person person) {
        PersonRepository repository = repositories.get(person.getType());
        if (repository == null) {
            throw new IllegalArgumentException("Unsupported person type: " + person.getType());
        }
        return repository.save(person);
    }

    public Person findPersonByMobile(String mobile) {
        String type = getTypeFromDatabase(mobile);
        PersonRepository repository = repositories.get(type);
        if (repository == null) {
            throw new IllegalArgumentException("Unsupported person type: " + type);
        }
        return repository.findByMobile(mobile);
    }

    public boolean updatePerson(String originalMobile, Person updatedPerson) {
        PersonRepository repository = repositories.get(updatedPerson.getType());
        if (repository == null) {
            throw new IllegalArgumentException("Unsupported person type: " + updatedPerson.getType());
        }
        return repository.update(originalMobile, updatedPerson);
    }

    public boolean deletePerson(String mobile) {
        String type = getTypeFromDatabase(mobile);
        PersonRepository repository = repositories.get(type);
        if (repository == null) {
            throw new IllegalArgumentException("Unsupported person type: " + type);
        }
        return repository.delete(mobile);
    }

    private String getTypeFromDatabase(String mobile) {
        String sql = "SELECT type FROM person WHERE mobile = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, mobile);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Person not found with mobile: " + mobile);
    }
}