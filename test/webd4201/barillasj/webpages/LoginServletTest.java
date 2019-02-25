/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webd4201.barillasj.webpages;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jaime
 */
public class LoginServletTest {
    Req req;
    Res res;
    LoginServlet login;
    
    /**
     * Reset request and response before each test.
     */
    @Before
    public void prepTests() {
        req = new Req();
        res = new Res();
        login = new LoginServlet();
    }
    
    /**
     * Test login logic.
     */
    @Test
    public void testDoPost() {
        System.out.println("Testing valid login");
        
        // Setup request parameters
        req.setParameter("studentId", "100505421");
        req.setParameter("studentPassword", "laserkl");
        
        // Executing log-in logic.
        try {
            login.doPost(req, res);
        } catch (Exception ex) {
            String msg = "";
            for (StackTraceElement el : ex.getStackTrace()) {
                msg += "\n";
                msg += el.toString();
            }
            fail("Error occured: " + ex.getMessage() + msg);
        }
        
        // Check results
        HttpSession session = req.getSession();
        String redirect = res.get("redirect");
        
        assertNotNull(session.getAttribute("user"));
        assertEquals("./dashboard.jsp", redirect);
    }
    
}
