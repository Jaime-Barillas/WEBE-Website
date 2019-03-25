package webd4201.barillasj.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import webd4201.barillasj.Mark;
import webd4201.barillasj.Student;
import webd4201.barillasj.User;
import webd4201.barillasj.WebLogger;
import webd4201.barillasj.webexceptions.DuplicateException;
import webd4201.barillasj.webexceptions.InvalidUserDataException;
import webd4201.barillasj.webexceptions.NotFoundException;

/**
 * Provides database access for the Student class.
 *
 * @author Jaime Barillas
 * @version 0.1.0 (2019/01/21)
 * @since 0.1.0
 */
public class StudentDA {
    //-------- Variables --------//
    /**
     * Connection to the database.
     */
    static Connection dbConnection;
    
    /**
     * SQL statement to create a new Student within the database.
     */
    static PreparedStatement sqlCreateStudent;
    
    /**
     * SQL statement to retrieve a Student from the database.
     */
    static PreparedStatement sqlRetrieveStudent;
    
    /**
     * SQL statement to update a Student within the database.
     */
    static PreparedStatement sqlUpdateStudent;
    
    /**
     * SQL statement to authenticate and retrieve a Student from the database.
     */
    static PreparedStatement sqlAuthenticateStudent;
    
    /***
     * SQL statement to update a student's password.
     */
    static PreparedStatement sqlUpdatePassword;
    
    /* The following variables temporarily hold the values of a student for
     * easier access with the below methods.
     */
    static Student student;
    static long id;
    static String password;
    static String firstName;
    static String lastName;
    static String emailAddress;
    static Date lastAccess;
    static Date enrollDate;
    static boolean enabled;
    static char type;
    static String programCode;
    static String programDescription;
    static int year;
    static Vector<Mark> marks;
    
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
            sqlCreateStudent = dbConnection.prepareCall(
                    "INSERT INTO students (id, programCode, programDescription, "
                            + "year) "
                            + "    VALUES (?, ?, ?, ?);"
            );

            sqlRetrieveStudent = dbConnection.prepareStatement(
                    "SELECT users.id, password, firstName, lastName, "
                            + "emailAddress, lastAccess, enrollDate, type, "
                            + "enabled, programCode, programDescription, year "
                            + "    FROM users INNER JOIN students"
                            + "    ON users.id = students.id WHERE users.id = ?;"
            );

            sqlUpdateStudent = dbConnection.prepareStatement(
                    "UPDATE students SET "
                            + "id = ?, programCode = ?, programDescription = ?, "
                            + "year = ?"
                            + "    WHERE id = ?;"
            );

            sqlAuthenticateStudent = dbConnection.prepareStatement(
                    "SELECT users.id, password, firstName, lastName, "
                            + "emailAddress, lastAccess, enrollDate, type, "
                            + "enabled, programCode, programDescription, year "
                            + "    FROM users INNER JOIN students"
                            + "    ON users.id = students.id WHERE users.id = ? and"
                            + "    password = ENCODE(DIGEST(?, 'sha1'), 'hex');"
            );
            
            sqlUpdatePassword = dbConnection.prepareStatement(
                    "UPDATE users SET "
                            + "password = ENCODE(DIGEST(?, 'sha1'), 'hex') "
                            + "WHERE id = ?;"
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
            sqlCreateStudent.close();
            sqlRetrieveStudent.close();
            sqlUpdateStudent.close();
            sqlAuthenticateStudent.close();
            sqlUpdatePassword.close();
        } catch(SQLException ex) {
            WebLogger.logError("Could not close prepared statements!", ex);
        }
    }
    
    /**
     * Creates a new student within the database.
     * 
     * @param newStudent (Student) The student to create.
     * @return true if creation of student is successful, false otherwise.
     *
     * @throws DuplicateException If the student already exists within the
     * database.
     */
    public static boolean create(Student newStudent) throws DuplicateException {
        boolean success = false;
        
        id = newStudent.getId();
        password = newStudent.getPassword();
        firstName = newStudent.getFirstName();
        lastName = newStudent.getLastName();
        emailAddress = newStudent.getEmailAddress();
        lastAccess = Date.valueOf(SQL_DF.format(newStudent.getLastAccess()));
        enrollDate = Date.valueOf(SQL_DF.format(newStudent.getEnrollDate()));
        enabled = newStudent.isEnabled();
        type = newStudent.getType();
        programCode = newStudent.getProgramCode();
        programDescription = newStudent.getProgramDescription();
        year = newStudent.getYear();
        
        try {
            // Should fail so we can insert a new student.
            retrieve(id);
            throw new DuplicateException("Could not create a new student with id: "
                    + id + ", one already exists within the database!");
        } catch (NotFoundException ex) {
            try {
                // Create a user first
                success = User.create(newStudent);
                
                if (success) {
                    sqlCreateStudent.setLong(1, id);
                    sqlCreateStudent.setString(2, programCode);
                    sqlCreateStudent.setString(3, programDescription);
                    sqlCreateStudent.setInt(4, year);
                    
                    success = sqlCreateStudent.executeUpdate() > 0;
                }
            } catch(SQLException ex2) {
                WebLogger.logError("Could not create a new student!", ex2);
            }
        }
        
        return success;
    }
    
    /**
     * Retrieves a student from the database.
     *
     * @param studentId (long) The id of the student to retrieve.
     * @return (Student) The requested student.
     *
     * @throws NotFoundException If the student does not exist within the
     * database.
     */
    public static Student retrieve(long studentId) throws NotFoundException {
        // Clear any previous student.
        student = null;

        try {
            // Set parameters, execute query.
            sqlRetrieveStudent.setLong(1, studentId);
            ResultSet result = sqlRetrieveStudent.executeQuery();
            
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
                programCode = result.getString("programCode");
                programDescription = result.getString("programDescription");
                year = result.getInt("year");
                
                // Create the student
                student = new Student(id, password, firstName, lastName,
                        emailAddress, lastAccess, enrollDate, type, enabled,
                        programCode, programDescription, year);
            } else {
                throw new NotFoundException("Student " + studentId
                        + " does not exist within the database!");
            }
        } catch(SQLException ex) {
            WebLogger.logError("Could not query the database for a student!", ex);
        } catch(InvalidUserDataException ex) {
            WebLogger.logError("Database record for student " + id
                    + " has invalid data! ", ex);
        }
        
        return student;
    }
    
    /**
     * Update the student in the database.
     *
     * @param studentToUpdate (Student) The student to update with the updated
     *                        values
     * @return (int) The number of rows updated (per table).
     * @throws NotFoundException If the student does not exist in the database.
     */
    public static int update(Student studentToUpdate) throws NotFoundException {
        int studentRowsUpdated = 0;
        
        try {
            // Ensure the student exists
            retrieve(studentToUpdate.getId());
            
            // Grab the new values from the student for easier access below.
            id = studentToUpdate.getId();
            password = studentToUpdate.getPassword();
            firstName = studentToUpdate.getFirstName();
            lastName = studentToUpdate.getLastName();
            emailAddress = studentToUpdate.getEmailAddress();
            lastAccess = Date.valueOf(SQL_DF.format(studentToUpdate.getLastAccess()));
            enrollDate = Date.valueOf(SQL_DF.format(studentToUpdate.getEnrollDate()));
            type = studentToUpdate.getType();
            enabled = studentToUpdate.isEnabled();
            programCode = studentToUpdate.getProgramCode();
            programDescription = studentToUpdate.getProgramDescription();
            year = studentToUpdate.getYear();
            
            try {
                // Update the user first.
                studentRowsUpdated = User.update(studentToUpdate);
                
                if (studentRowsUpdated > 0) {
                    sqlUpdateStudent.setLong(1, id);
                    sqlUpdateStudent.setString(2, programCode);
                    sqlUpdateStudent.setString(3, programDescription);
                    sqlUpdateStudent.setInt(4, year);
                    sqlUpdateStudent.setLong(5, id);
                
                    // The amount of rows affected is the sum of the user and
                    // student update.
                    studentRowsUpdated += sqlUpdateStudent.executeUpdate();
                }
            } catch (SQLException ex) {
                WebLogger.logError("Could not update student " + id + "!", ex);
            }
        } catch (NotFoundException ex) {
            throw new NotFoundException("Student " + id + " cannot be updated, "
                    + "he/she does not exist in the database.");
        }
        
        return studentRowsUpdated;
    }
    
    /**
     * Deletes the specified student from the database.
     *
     * @param studentToDelete (Student) The student to delete.
     * @return (int) Number of rows affected (per table).
     * @throws NotFoundException If the student does not exist in the database.
     */
    public static int delete(Student studentToDelete) throws NotFoundException {
        int rowsDeleted = 0;
        
        rowsDeleted = User.delete(studentToDelete);
        
        return rowsDeleted;
    }

    /**
     * Authenticates a student with the given id and password. If such a student
     * exists, then the corresponding Student is returned.
     *
     * @param studentId       The id of the student to authenticate.
     * @param studentPassword The student's password.
     * @return The authenticated Student.
     * @throws NotFoundException If the student id + password combo is incorrect.
     */
    public static Student authenticate(long studentId, String studentPassword)
            throws NotFoundException {
        try {
            sqlAuthenticateStudent.setLong(1, studentId);
            sqlAuthenticateStudent.setString(2, studentPassword);
            ResultSet result = sqlAuthenticateStudent.executeQuery();
            
            if (result.next()) {
                id = result.getLong("id");
                password = result.getString("password");
                firstName = result.getString("firstName");
                lastName = result.getString("lastName");
                emailAddress = result.getString("emailAddress");
                lastAccess = result.getDate("lastAccess");
                enrollDate = result.getDate("enrollDate");
                enabled = result.getBoolean("enabled");
                type = result.getString("type").charAt(0);
                programCode = result.getString("programCode");
                programDescription = result.getString("programDescription");
                year = result.getInt("year");
                
                // Create the student
                student = new Student(id, password, firstName, lastName,
                        emailAddress, lastAccess, enrollDate, type, enabled,
                        programCode, programDescription, year);
            } else {
                throw new NotFoundException("Could not find a student with the "
                        + "provided id and password combination!");
            }
        } catch (SQLException ex) {
            WebLogger.logError("Could not authenticate the student!", ex);
        } catch (InvalidUserDataException ex) {
            WebLogger.logError("Database record for student " + id
                    + " has invalid data!", ex);
        }
        
        return student;
    }
    
    /**
     * Updates the password for a Student.
     *
     * @param studentId (long) The id of the student.
     * @param newPassword (String) the new password for the student.
     * @return The student with the new password or null on failure.
     */
    public static Student updatePassword(long studentId, String newPassword) {
        student = null;
        
        // If the query succeeded then we return a new Student with the
        // updated password, otherwise we return null.
        try {
            sqlUpdatePassword.setString(1, newPassword);
            sqlUpdatePassword.setLong(2, studentId);
            
            ResultSet result = sqlUpdatePassword.executeQuery();
            
            if (result.next()) {
                id = result.getLong("id");
                password = result.getString("password");
                firstName = result.getString("firstName");
                lastName = result.getString("lastName");
                emailAddress = result.getString("emailAddress");
                lastAccess = result.getDate("lastAccess");
                enrollDate = result.getDate("enrollDate");
                enabled = result.getBoolean("enabled");
                type = result.getString("type").charAt(0);
                programCode = result.getString("programCode");
                programDescription = result.getString("programDescription");
                year = result.getInt("year");
                
                student = new Student(id, password, firstName, lastName,
                        emailAddress, lastAccess, enrollDate, type, enabled,
                        programCode, programDescription, year);
                
                // Commit to the changes! Or not...
                if (student != null) {
                    dbConnection.commit();
                } else {
                    dbConnection.rollback();
                }
            }
        } catch (SQLException ex) {
            WebLogger.logError("Could not update student password!", ex);
        } catch (InvalidUserDataException ex) {
            WebLogger.logError("The student contains invalid data!", ex);
        }
        
        return student;
    }
}
