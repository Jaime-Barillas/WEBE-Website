package webd4201.barillasj.webpages;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import webd4201.barillasj.Student;
import webd4201.barillasj.User;
import javax.servlet.ServletException;

/**
 * Manages password updates.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-03-11
 */
public class UpdatePasswordServlet extends HttpServlet {
    
    /**
     * Updates the user's password.
     *
     * @param req
     * @param res 
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws
            IOException, ServletException{
        String oldPassword = Util.getReqParamOrDefault(req, "oldPassword", "");
        String newPassword = Util.getReqParamOrDefault(req, "newPassword", "");
        String confirmPassword = Util.getReqParamOrDefault(req, "confirmPassword", "");
        Student student = null;
        HttpSession session = req.getSession();
        boolean error = false; 
        
        if (oldPassword.isEmpty()) {
            session.setAttribute("updatePasswordMsg", "You must enter your old password!");
            throw new IOException("Test");
        } else if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            session.setAttribute("updatePasswordMsg", "You must enter a new password and confirm it!");
            error = true;
        } else if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("updatePasswordMsg", "The new and confirm passwords should be the same!");
            error = true;
        } else if (newPassword.length() < User.MINIMUM_PASSWORD_LENGTH ||
                   newPassword.length() > User.MAXIMUM_PASSWORD_LENGTH) {
            session.setAttribute("updatePasswordMsg", "The new password must be between "
                    + User.MINIMUM_PASSWORD_LENGTH + " and " + User.MAXIMUM_PASSWORD_LENGTH
                    + " characters long!");
            error = true;
        } else {
            // Check session for correct password.
            student = (Student)session.getAttribute("user");
            try {
                Student.authenticate(student.getId(), oldPassword);
            } catch (Exception ex) {
                session.setAttribute("updatePasswordMsg", "The old password does not match your current password!");
                error = true;
            }
        }
        
        if (!error) {
            student = Student.updatePassword(student.getId(), newPassword);
                
            session.setAttribute("user", student);
            session.setAttribute("dashboardMsg", "Password successfully updated!");
               
            res.sendRedirect("./dashboard.jsp");
        } else {
            res.sendRedirect("./update-password.jsp");
        }
    }
}
