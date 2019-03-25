package webd4201.barillasj.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import webd4201.barillasj.User;
import webd4201.barillasj.WebLogger;
import webd4201.barillasj.webexceptions.DuplicateException;
import webd4201.barillasj.webexceptions.InvalidUserDataException;
import webd4201.barillasj.webexceptions.NotFoundException;

/**
 * Provides database access for the User class.
 *
 * @author Jaime Barillas
 * @version 0.1.0 (2019/03/25)
 * @since 0.1.0
 */
public class UserDA {
    //-------- Variables --------//
    /**
     * Connection to the database.
     */
    static Connection dbConnection;
    
    /**
     * SQL statement to create a new User within the database.
     */
    static PreparedStatement sqlCreateUser;
    
    /**
     * SQL statement to retrieve a User from the database.
     */
    static PreparedStatement sqlRetrieveUser;
    
    /**
     * SQL statement to update a User within the database.
     */
    static PreparedStatement sqlUpdateUser;
    
    /**
     * SQL statement to delete a User from the database.
     * This will delete the associated Student through the Cascading Delete
     * constraint placed on the student.
     */
    static PreparedStatement sqlDeleteUser;
    
    /* The following variables temporarily hold the values of a student for
     * easier access with the below methods.
     */
    static User user;
    static long id;
    static String password;
    static String firstName;
    static String lastName;
    static String emailAddress;
    static Date lastAccess;
    static Date enrollDate;
    static boolean enabled;
    static char type;
    
    /**
     * Formats generic java.util.Date to a format accepted by
     * java.sql.Date.valueOf().
     */
    private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Initializes the prepared statements for database access.
     * @param connection The db connection to access.
     */
    public static void initialize(Connection connection) {
        dbConnection = connection;

        try {
            sqlCreateUser = dbConnection.prepareStatement(
                    "INSERT INTO users (id, password, firstName, lastName, "
                            + "emailAddress, lastAccess, enrollDate, type, "
                            + "enabled) "
                            + "    VALUES (?, ENCODE(DIGEST(?, 'sha1'), 'hex'), "
                            + "    ?, ?, ?, ?, ?, ?, ?);"
            );

            sqlRetrieveUser = dbConnection.prepareStatement(
                    "SELECT users.id, password, firstName, lastName, "
                            + "emailAddress, lastAccess, enrollDate, type, "
                            + "enabled, programCode, programDescription, year "
                            + "    FROM users INNER JOIN students"
                            + "    ON users.id = students.id WHERE users.id = ?;"
            );

            sqlUpdateUser = dbConnection.prepareStatement(
                    "UPDATE users SET "
                            + "id = ?, password = "
                            + "ENCODE(DIGEST(?, 'sha1'), 'hex'), firstName = ?, "
                            + "lastName = ?, emailAddress = ?, lastAccess = ?, "
                            + "enrollDate = ?, type = ?, enabled = ?"
                            + "    WHERE id = ?;"
            );

            sqlDeleteUser = dbConnection.prepareStatement(
                    "DELETE FROM users WHERE id = ?;"
            );
        } catch (SQLException ex) {
            WebLogger.logError("Could not create prepared statements!", ex);
        }
    }
    
    /**
     * Closes all prepared statements.
     */
    public static void terminate() {
        try {
            sqlCreateUser.close();
            sqlRetrieveUser.close();
            sqlUpdateUser.close();
            sqlDeleteUser.close();
        } catch(SQLException ex) {
            WebLogger.logError("Could not close prepared statements!", ex);
        }
    }
    
    /**
     * Creates a new student within the database.
     * 
     * @param newUser (User) The user to create.
     * @return true if creation of user is successful, false otherwise.
     *
     * @throws DuplicateException If the user already exists within the
     * database.
     */
    public static boolean create(User newUser) throws DuplicateException {
        boolean success = false;
        
        id = newUser.getId();
        password = newUser.getPassword();
        firstName = newUser.getFirstName();
        lastName = newUser.getLastName();
        emailAddress = newUser.getEmailAddress();
        lastAccess = Date.valueOf(SQL_DF.format(newUser.getLastAccess()));
        enrollDate = Date.valueOf(SQL_DF.format(newUser.getEnrollDate()));
        enabled = newUser.isEnabled();
        type = newUser.getType();
        
        try {
            // Should fail so we can insert a new student.
            retrieve(id);
            throw new DuplicateException("Could not create a new user with id: "
                    + id + ", one already exists within the database!");
        } catch (NotFoundException ex) {
            try {
                // Set the parameters.
                sqlCreateUser.setLong(1, id);
                sqlCreateUser.setString(2, password);
                sqlCreateUser.setString(3, firstName);
                sqlCreateUser.setString(4, lastName);
                sqlCreateUser.setString(5, emailAddress);
                sqlCreateUser.setDate(6, lastAccess);
                sqlCreateUser.setDate(7, enrollDate);
                sqlCreateUser.setString(8, String.valueOf(type));
                sqlCreateUser.setBoolean(9, enabled);
                
                // Execute the SQL statements.
                success = (sqlCreateUser.executeUpdate() > 0);
            } catch(SQLException ex2) {
                WebLogger.logError("Could not create a new student!", ex2);
            }
        }
        
        return success;
    }
    
    /**
     * Retrieves a User from the database.
     *
     * @param userId (long) The id of the student to retrieve.
     * @return (User) The requested user.
     *
     * @throws NotFoundException If the student does not exist within the
     * database.
     */
    public static User retrieve(long userId) throws NotFoundException {
        // Clear any previous student.
        user = null;

        try {
            // Set parameters, execute query.
            sqlRetrieveUser.setLong(1, userId);
            ResultSet result = sqlRetrieveUser.executeQuery();
            
            if(result.next()) {
                id = result.getLong("id");
                password = result.getString("password");
                firstName = result.getString("firstName");
                lastName = result.getString("lastName");
                emailAddress = result.getString("emailAddress");
                lastAccess = result.getDate("lastAccess");
                enrollDate = result.getDate("enrollDate");
                enabled = result.getBoolean("enabled");
                type = result.getString("type").charAt(0);
                
                // Create the student
                user = new User(id, password, firstName, lastName,
                        emailAddress, lastAccess, enrollDate, type, enabled);
            } else {
                throw new NotFoundException("Student " + userId
                        + " does not exist within the database!");
            }
        } catch(SQLException ex) {
            WebLogger.logError("Could not query the database for a student!", ex);
        } catch(InvalidUserDataException ex) {
            WebLogger.logError("Database record for student " + id
                    + " has invalid data! ", ex);
        }
        
        return user;
    }
    
    /**
     * Update the User in the database.
     *
     * @param userToUpdate (User) The user to update with the updated
     *                        values
     * @return (int) The number of rows updated (per table).
     * @throws NotFoundException If the user does not exist in the database.
     */
    public static int update(User userToUpdate) throws NotFoundException {
        int userRowsUpdated = 0;
        
        try {
            // Ensure the student exists
            retrieve(userToUpdate.getId());
            
            // Grab the new values from the student for easier access below.
            id = userToUpdate.getId();
            password = userToUpdate.getPassword();
            firstName = userToUpdate.getFirstName();
            lastName = userToUpdate.getLastName();
            emailAddress = userToUpdate.getEmailAddress();
            lastAccess = Date.valueOf(SQL_DF.format(userToUpdate.getLastAccess()));
            enrollDate = Date.valueOf(SQL_DF.format(userToUpdate.getEnrollDate()));
            type = userToUpdate.getType();
            enabled = userToUpdate.isEnabled();
            
            try {
                // Set parameters.
                sqlUpdateUser.setLong(1, id);
                sqlUpdateUser.setString(2, password);
                sqlUpdateUser.setString(3, firstName);
                sqlUpdateUser.setString(4, lastName);
                sqlUpdateUser.setString(5, emailAddress);
                sqlUpdateUser.setDate(6, lastAccess);
                sqlUpdateUser.setDate(7, enrollDate);
                sqlUpdateUser.setString(8, String.valueOf(type));
                sqlUpdateUser.setBoolean(9, enabled);
                sqlUpdateUser.setLong(10, id);
                
                // Execute query.
                userRowsUpdated = sqlUpdateUser.executeUpdate();
            } catch (SQLException ex) {
                WebLogger.logError("Could not update student " + id + "!", ex);
            }
        } catch (NotFoundException ex) {
            throw new NotFoundException("Student " + id + " cannot be updated, "
                    + "he/she does not exist in the database.");
        }
        
        return userRowsUpdated;
    }
    
    /**
     * Deletes the specified User from the database.
     *
     * @param userToDelete (User) The user to delete.
     * @return (int) Number of rows affected (per table).
     * @throws NotFoundException If the user does not exist in the database.
     */
    public static int delete(User userToDelete) throws NotFoundException {
        int rowsDeleted = 0;
        
        try {
            // Ensure student exists.
            retrieve(userToDelete.getId());
            
            try {
                // Set parameters, execute query.
                sqlDeleteUser.setLong(1, id);
                rowsDeleted = sqlDeleteUser.executeUpdate();
            } catch (SQLException ex) {
                WebLogger.logError("Could not delete student " + id + "!", ex);
            }
        } catch (NotFoundException ex) {
            throw new NotFoundException("Student " + userToDelete.getId()
                    + " cannot be deleted, he/she does not exist in the database.");
        }
        
        return rowsDeleted;
    }
}
