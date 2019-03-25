package webd4201.barillasj.webpages;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import webd4201.barillasj.Student;
import webd4201.barillasj.webexceptions.NotFoundException;

/**
 * Handles the registering logic.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-02-25
 */
public class RegisterServlet extends HttpServlet {
    
    /**
     * Register a new Student.
     *
     * @param req Http request provided by the server.
     * @param res Http response provided by the server.
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        
        res.sendRedirect("./dashboard.jsp");
    }
}
