package webd4201.barillasj.webpages;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Handles the log out logic.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-02-25
 */
public class LogoutServlet extends HttpServlet {
    
    /**
     * Log out the current user from the system.
     * Redirects to the login page.
     *
     * @param req Http request object provided by the server.
     * @param res Http response object provided by the server.
     * @throws IOException 
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        HttpSession session = req.getSession();
        
        session.removeAttribute("user");
        session.setAttribute("loginError", "You have successfully logged out!");
        
        res.sendRedirect("./login.jsp");
    }
}
