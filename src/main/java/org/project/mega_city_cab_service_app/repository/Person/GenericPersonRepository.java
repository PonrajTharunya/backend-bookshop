//package org.project.mega_city_cab_service_app.repository.Person;
//
//import org.project.mega_city_cab_service_app.model.Parent.Person;
//import org.project.mega_city_cab_service_app.util.DBConnection;
//
//import java.sql.*;
//
//public abstract class GenericPersonRepository<T extends Person> implements PersonRepository {
//    protected final DBConnection dbConnection;
//    private final Class<T> typeToken; // Type token for T
//
//    public GenericPersonRepository(DBConnection dbConnection, Class<T> typeToken) {
//        this.dbConnection = dbConnection;
//        this.typeToken = typeToken;
//    }
//
//    @Override
//    public boolean save(Person person) {
//        if (!typeToken.isInstance(person)) { // Safely check if person is of type T
//            throw new IllegalArgumentException("Invalid person type");
//        }
//
//        Connection connection = null;
//        try {
//            connection = dbConnection.getConnection();
//            connection.setAutoCommit(false);
//
//            // Save to `person` table
//            String personSql = "INSERT INTO person (type, name, address, mobile, username, password) VALUES (?, ?, ?, ?, ?, ?)";
//            try (PreparedStatement personStatement = connection.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS)) {
//                personStatement.setString(1, person.getType());
//                personStatement.setString(2, person.getName());
//                personStatement.setString(3, person.getAddress());
//                personStatement.setString(4, person.getMobile());
//                personStatement.setString(5, person.getUsername());
//                personStatement.setString(6, person.getPassword());
//                personStatement.executeUpdate();
//
//                ResultSet generatedKeys = personStatement.getGeneratedKeys();
//                if (!generatedKeys.next()) {
//                    throw new SQLException("Failed to retrieve generated keys.");
//                }
//                int personId = generatedKeys.getInt(1);
//
//                // Delegate to a method for saving entity-specific data
//                saveEntitySpecificData(connection, personId, typeToken.cast(person)); // Safe cast using type token
//            }
//
//            connection.commit();
//            return true;
//        } catch (SQLException e) {
//            rollback(connection);
//            e.printStackTrace();
//            return false;
//        } finally {
//            resetAutoCommit(connection);
//        }
//    }
//
//    @Override
//    public Person findByMobile(String mobile) {
//        String personSql = "SELECT * FROM person WHERE mobile = ?";
//
//        try (Connection connection = dbConnection.getConnection();
//             PreparedStatement personStatement = connection.prepareStatement(personSql)) {
//
//            personStatement.setString(1, mobile);
//            ResultSet personResultSet = personStatement.executeQuery();
//
//            if (!personResultSet.next()) {
//                return null; // No person found
//            }
//
//            int id = personResultSet.getInt("id");
//            String name = personResultSet.getString("name");
//            String address = personResultSet.getString("address");
//            String username = personResultSet.getString("username");
//            String password = personResultSet.getString("password");
//
//            // Delegate to a method for loading entity-specific data
//            return loadEntitySpecificData(id, name, address, mobile, username, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public boolean update(String originalMobile, Person updatedPerson) {
//        if (!typeToken.isInstance(updatedPerson)) { // Safely check if updatedPerson is of type T
//            throw new IllegalArgumentException("Invalid person type");
//        }
//
//        Connection connection = null;
//        try {
//            connection = dbConnection.getConnection();
//            connection.setAutoCommit(false);
//
//            // Update `person` table
//            String personSql = "UPDATE person SET name = ?, address = ?, mobile = ?, username = ?, password = ? WHERE mobile = ?";
//            try (PreparedStatement personStatement = connection.prepareStatement(personSql)) {
//                personStatement.setString(1, updatedPerson.getName());
//                personStatement.setString(2, updatedPerson.getAddress());
//                personStatement.setString(3, updatedPerson.getMobile());
//                personStatement.setString(4, updatedPerson.getUsername());
//                personStatement.setString(5, updatedPerson.getPassword());
//                personStatement.setString(6, originalMobile);
//                personStatement.executeUpdate();
//            }
//
//            // Retrieve ID of the person
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
//            // Delegate to a method for updating entity-specific data
//            updateEntitySpecificData(connection, personId, typeToken.cast(updatedPerson)); // Safe cast using type token
//
//            connection.commit();
//            return true;
//        } catch (SQLException e) {
//            rollback(connection);
//            e.printStackTrace();
//            return false;
//        } finally {
//            resetAutoCommit(connection);
//        }
//    }
//
//    @Override
//    public boolean delete(String mobile) {
//        Connection connection = null;
//        try {
//            connection = dbConnection.getConnection();
//            connection.setAutoCommit(false);
//
//            // Retrieve ID of the person
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
//            // Delegate to a method for deleting entity-specific data
//            deleteEntitySpecificData(connection, personId);
//
//            // Delete from `person` table
//            String personSql = "DELETE FROM person WHERE mobile = ?";
//            try (PreparedStatement personStatement = connection.prepareStatement(personSql)) {
//                personStatement.setString(1, mobile);
//                personStatement.executeUpdate();
//            }
//
//            connection.commit();
//            return true;
//        } catch (SQLException e) {
//            rollback(connection);
//            e.printStackTrace();
//            return false;
//        } finally {
//            resetAutoCommit(connection);
//        }
//    }
//
//    // Abstract methods for entity-specific behavior
//    protected abstract void saveEntitySpecificData(Connection connection, int personId, T person) throws SQLException;
//    protected abstract T loadEntitySpecificData(int id, String name, String address, String mobile, String username, String password) throws SQLException;
//    protected abstract void updateEntitySpecificData(Connection connection, int personId, T updatedPerson) throws SQLException;
//    protected abstract void deleteEntitySpecificData(Connection connection, int personId) throws SQLException;
//
//    private void rollback(Connection connection) {
//        if (connection != null) {
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void resetAutoCommit(Connection connection) {
//        if (connection != null) {
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}