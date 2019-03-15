package webd4201.barillasj;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Logs any messages to a file in the project folder.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-03-11
 */
@WebListener
public class WebLogger implements ServletContextListener {
    private static Logger logger = null;
    
    /**
     * Log a message at the info level.
     *
     * @param msg (String) The message to log.
     */
    public static void logInfo(String msg) {
        if (logger != null) {
            logger.log(Level.INFO, msg);
        }
    }
    
    /**
     * Log a message at the info level and print the exception's message.
     *
     * @param msg (String) The message to log.
     * @param ex The exception that was thrown.
     */
    public static void logInfo(String msg, Exception ex) {
        logInfo(msg + "\n\tDetails: " + ex.getMessage());
    }
    
    /**
     * Log a message at the warning level.
     *
     * @param msg (String) The message to log.
     */
    public static void logWarning(String msg) {
        if (logger != null) {
            logger.log(Level.WARNING, msg);
        }
    }
    
    /**
     * Log a message at the warning level and print the exception's message.
     *
     * @param msg (String) The message to log.
     * @param ex The exception that was thrown.
     */
    public static void logWarning(String msg, Exception ex) {
        logWarning(msg + "\n\tDetails: " + ex.getMessage());
    }
    
    /**
     * Log a message at the severe level.
     *
     * @param msg (String) The message to log.
     */
    public static void logError(String msg) {
        if (logger != null) {
            logger.log(Level.SEVERE, msg);
        }
    }
    
    /**
     * Log a message at the error level and print the exception's message.
     *
     * @param msg (String) The message to log.
     * @param ex The exception that was thrown.
     */
    public static void logError(String msg, Exception ex) {
        logError(msg + "\n\tDetails: " + ex.getMessage());
    }
    
    /**
     * Sets up the logger and log server startup.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initalize the logger
        String logFilePath = System.getProperty("catalina.base")
                + "\\webapps\\barillasj\\logs\\log";
        
        try {
            FileHandler fh = new FileHandler(logFilePath);
            fh.setFormatter(new SimpleFormatter());
            logger = Logger.getLogger(this.getClass().getName());
            logger.addHandler(fh);
            
            logInfo("Web Server Started");
        } catch (IOException ex) {
            System.err.println("Unable to initialize the logger!\n\t"
                    + ex.getMessage());
        }
    }

    /**
     * Logs server shutdown.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (logger != null) {
            logInfo("Web Server Shutdown");
        }
    }
}
