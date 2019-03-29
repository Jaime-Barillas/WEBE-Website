package webd4201.barillasj.webpages;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import webd4201.barillasj.db.DatabaseConnect;
import webd4201.barillasj.db.StudentDA;
import webd4201.barillasj.db.UserDA;

/**
 * Manages any resources that this webapp might need.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-02-25
 */
@WebListener
public class ResourceManager implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initalize a database connection and the student db access class.
        
        StudentDA.initialize(DatabaseConnect.initialize());
        UserDA.initialize(DatabaseConnect.getDbConnection());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Close resources held by the student db access class and the database connection.
        UserDA.terminate();
        StudentDA.terminate();
        DatabaseConnect.terminate();
    }

}
