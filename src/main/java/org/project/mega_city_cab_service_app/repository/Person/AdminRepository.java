package org.project.mega_city_cab_service_app.repository.Person;

import org.project.mega_city_cab_service_app.model.person.Admin;
import org.project.mega_city_cab_service_app.model.Parent.Person;
import org.project.mega_city_cab_service_app.model.person.Customer;
import org.project.mega_city_cab_service_app.util.DBConnection;

import java.sql.*;

public class AdminRepository implements PersonRepository {
    private final DBConnection dbConnection;

    public AdminRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public boolean save(Person person) {
        if (!(person instanceof Admin admin)) {
            throw new IllegalArgumentException("Invalid person type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Save to `person` table
            String personSql = "INSERT INTO person (type, name, address, mobile,  username , password) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement personStatement = connection.prepareStatement(personSql)) {
                personStatement.setString(1, person.getType());
                personStatement.setString(2, person.getName());
                personStatement.setString(3, person.getAddress());
                personStatement.setString(4, person.getMobile());
                personStatement.setString(5, person.getUsername());
                personStatement.setString(6, person.getPassword());

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

    @Override
    public Person findByMobile(String mobile) {
        String personSql = "SELECT * FROM person WHERE mobile = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement personStatement = connection.prepareStatement(personSql)) {

            personStatement.setString(1, mobile);
            ResultSet personResultSet = personStatement.executeQuery();

            if (personResultSet.next()) {
                String name = personResultSet.getString("name");
                String address = personResultSet.getString("address");
                String  username= personResultSet.getString("username");
                String password = personResultSet.getString("password");
                return new Admin(name, address, mobile,username,password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person findById(int id) {
        String personSql = "SELECT * FROM person WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement personStatement = connection.prepareStatement(personSql)) {

            personStatement.setInt(1, id);
            ResultSet personResultSet = personStatement.executeQuery();

            if (personResultSet.next()) {
                String name = personResultSet.getString("name");
                String address = personResultSet.getString("address");
                String mobile = personResultSet.getString("mobile");
                String username = personResultSet.getString("username");
                String password = personResultSet.getString("password");

                return new Admin(name, address, mobile, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean update(String originalMobile, Person updatedPerson) {
        if (!(updatedPerson instanceof Admin admin)) {
            throw new IllegalArgumentException("Invalid person type");
        }

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // Update `person` table
            String personSql = "UPDATE person SET name = ?, address = ?, mobile = ? ,  username = ? , password = ? WHERE mobile = ?";
            try (PreparedStatement personStatement = connection.prepareStatement(personSql)) {
                personStatement.setString(1, updatedPerson.getName());
                personStatement.setString(2, updatedPerson.getAddress());
                personStatement.setString(3, updatedPerson.getMobile());
                personStatement.setString(4, originalMobile);
                personStatement.setString(5, updatedPerson.getUsername());
                personStatement.setString(6, updatedPerson.getPassword());
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

    @Override
    public boolean delete(String mobile) {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

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


//package org.project.mega_city_cab_service_app.repository.Person;
//
//import org.project.mega_city_cab_service_app.model.person.Admin;
//import org.project.mega_city_cab_service_app.model.Parent.Person;
//import org.project.mega_city_cab_service_app.util.DBConnection;
//import java.sql.*;
//
//public class AdminRepository extends GenericPersonRepository<Admin> {
//
//    public AdminRepository(DBConnection dbConnection) {
//        super(dbConnection, Admin.class); // Pass the type token for Admin
//    }
//
//    @Override
//    protected void saveEntitySpecificData(Connection connection, int personId, Admin person) throws SQLException {
//        // No additional tables for Admin
//    }
//
//    @Override
//    protected Admin loadEntitySpecificData(int id, String name, String address, String mobile, String username, String password) throws SQLException {
//        return new Admin(name, address, mobile, username, password);
//    }
//
//    @Override
//    protected void updateEntitySpecificData(Connection connection, int personId, Admin updatedPerson) throws SQLException {
//        // No additional tables for Admin
//    }
//
//    @Override
//    protected void deleteEntitySpecificData(Connection connection, int personId) throws SQLException {
//        // No additional tables for Admin
//    }
//}