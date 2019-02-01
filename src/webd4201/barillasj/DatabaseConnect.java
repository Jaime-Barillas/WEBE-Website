package webd4201.barillasj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages a connection to the system's database.
 *
 * @author Jaime Barillas
 * @version 0.1.0 (2019/01/21)
 * @since 0.1.0
 */
public class DatabaseConnect {
    //-------- Variables --------//
    /**
     * jdbc driver.
     */
    private static final String DRIVER = "org.postgresql.Driver";
    
    /**
     * Database location.
     */
    static String URL = "jdbc:postgresql://127.0.0.1:5432/webd4201_db";
    
    /**
     * Database user.
     */
    static String USER = "webd4201_admin";
    
    /**
     * Database password.
     */
    static String PASSWORD = "webd4201_password";
    
    /**
     * Connection object to the database.
     */
    static Connection dbConnection;

    //-------- Methods --------//
    /**
     * Connects to the database.
     *
     * @return (Connection) Connection to the database.
     */
    public static Connection initialize() {
        try {
            Class.forName(DRIVER);
            dbConnection =  DriverManager.getConnection(URL, USER, PASSWORD);
            dbConnection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DatabaseConnect.class.getName())
            //        .log(Level.SEVERE, null, ex);
            System.err.println("Could not connect to the database!\nError: " + ex.getMessage());
        }
        
        return dbConnection;
    }

    /**
     * Closes the connection to the database.
     */
    public static void terminate() {
        try {
            dbConnection.close();
        } catch (SQLException ex) {
            //Logger.getLogger(DatabaseConnect.class.getName())
            //        .log(Level.WARNING, null, ex);
            System.err.println("Could not connect to the database!\nError: " + ex.getMessage());
        }
    }
}
