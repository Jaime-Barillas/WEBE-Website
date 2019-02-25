/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webd4201.barillasj.webpages;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import webd4201.barillasj.db.DatabaseConnect;
import webd4201.barillasj.db.StudentDA;

/**
 *
 * @author jaime
 */
public class LogoutServletTest {
    Req req;
    Res res;
    LogoutServlet logout;
    
    /**
     * Connect to database for tests.
     */
    @BeforeClass
    public static void setUpClass() {
        StudentDA.initialize(DatabaseConnect.initialize());
    }
    
    /**
     * Terminate database connection.
     */
    @AfterClass
    public static void tearDownClass() {
        StudentDA.terminate();
        DatabaseConnect.terminate();
    }
    
    /**
     * Reset request and response before each test.
     */
    @Before
    public void prepTests() {
        req = new Req();
        res = new Res();
        logout = new LogoutServlet();
    }

    /**
     * Test logout logic.
     */
    @Test
    public void testDoGet() {
        System.out.println("Testing logout");
        
        // Setup an account to log out.
        req.getSession().setAttribute("user", "Some Student");
        
        // Executing log-out logic.
        try {
            logout.doGet(req, res);
        } catch (Exception ex) {
            String msg = "";
            for (StackTraceElement el : ex.getStackTrace()) {
                msg += "\n";
                msg += el.toString();
            }
            fail("Error occured: " + ex.getMessage() + msg);
        }
        
        // Check that LogoutServlet removed user and redirected to login.jsp.
        assertNull(req.getSession().getAttribute("user"));
        assertEquals("./login.jsp", res.get("redirect"));
    }
    
}
