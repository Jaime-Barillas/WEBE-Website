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
 * Handles the login logic.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-02-25
 */
public class LoginServlet extends HttpServlet {
    
    /**
     * Log in the user.
     * If the id + password combo is incorrect, an error message will be
     * displayed. Upon a successfull login, redirects to the dashboard page.
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
        // Grab the submitted id and password.
        Student student = null;
        long studentId = Util.getReqParam(req, "studentId", -1L);
        String studentPassword = Util.getReqParam(req,
                "studentPassword", "");
        
        // Attempt to log in.
        try {
            student = Student.authenticate(studentId, studentPassword);
        } catch (NotFoundException ex) { /* Log unsuccessful log-in attempt */ }
        
        // Set student in session if log in succeeds.
        if (student != null) {
            session.setAttribute("user", student);
            res.sendRedirect("./dashboard.jsp");
        } else {
            // Otherwise we ensure the session does not have a "user" attribute,
            // add an error message to the session, and redirect to the login
            // page.
            session.removeAttribute("user");
            session.setAttribute("loginError", "Did not find an account with "
                    + "that ID and Password combination!");
            res.sendRedirect("./login.jsp");
        }
    }
}
