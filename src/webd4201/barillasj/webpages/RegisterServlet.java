package webd4201.barillasj.webpages;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import webd4201.barillasj.Student;
import webd4201.barillasj.User;
import webd4201.barillasj.WebLogger;
import webd4201.barillasj.db.DatabaseConnect;
import webd4201.barillasj.webexceptions.DuplicateException;
import webd4201.barillasj.webexceptions.InvalidUserDataException;
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
        String id = Util.getReqParam(req, "id", "");
        String firstName = Util.getReqParam(req, "firstName", "");
        String lastName = Util.getReqParam(req, "lastName", "");
        String password = Util.getReqParam(req, "password", "");
        String confirmPassword = Util.getReqParam(req, "confirmPassword", "");
        String email = Util.getReqParam(req, "email", "");
        String year = Util.getReqParam(req, "year", "");
        String programCode = Util.getReqParam(req, "programCode", "");
        String programDescription = Util.getReqParam(req, "programDescription", "");
        boolean success = true;
        
        long studentId = 0;
        int studentYear = 0;
        
        // To start we clear any previous error messages and assume all values
        // are valid, so will put them in the session and remove them later if
        // we find they are not valid.
        session.removeAttribute("registerIdErr");
        session.removeAttribute("registerFirstNameErr");
        session.removeAttribute("registerLastNameErr");
        session.removeAttribute("registerPasswordErr");
        session.removeAttribute("registerConfirmPasswordErr");
        session.removeAttribute("registerEmailErr");
        session.removeAttribute("registerYearErr");
        session.removeAttribute("registerProgramCodeErr");
        session.removeAttribute("registerProgramDescriptionErr");
        
        session.setAttribute("registerId", id);
        session.setAttribute("registerFirstName", firstName);
        session.setAttribute("registerLastName", lastName);
        session.setAttribute("registerPassword", password);
        session.setAttribute("registerConfirmPassword", confirmPassword);
        session.setAttribute("registerEmail", email);
        session.setAttribute("registerYear", year);
        session.setAttribute("registerProgramCode", programCode);
        session.setAttribute("registerProgramDescription", programDescription);
        
        // Now we check for any errors and remove the values from the session if
        // we find any.
        if (id.isEmpty()) {
            session.setAttribute("registerIdErr", "* Student Id cannot be empty!");
            session.removeAttribute("registerId");
            success = false;
        } else {
            try {
                studentId = Long.parseLong(id);
                
                if (studentId < User.MINIMUM_ID_NUMBER ||
                    studentId > User.MAXIMUM_ID_NUMBER) {
                    session.setAttribute("registerIdErr", "* Student Id must be"
                            + " between " + User.MINIMUM_ID_NUMBER + " and "
                            + User.MAXIMUM_ID_NUMBER + "!");
                    session.removeAttribute("registerId");
                    success = false;
                }
            } catch (NumberFormatException ex) {
                session.setAttribute("registerIdErr", "* Student Id cannot be empty!");
                session.removeAttribute("registerId");
                success = false;
            }
        }
        
        if (firstName.isEmpty()) {
            session.setAttribute("registerFirstNameErr", "* First name cannot be empty!");
            session.removeAttribute("registerFirstName");
            success = false;
        }
        
        if (lastName.isEmpty()) {
            session.setAttribute("registerLastNameErr", "* Last name cannot be empty!");
            session.removeAttribute("registerLastName");
            success = false;
        }
        
        if (password.isEmpty()) {
            session.setAttribute("registerPasswordErr", "* Password cannot be empty!");
            session.removeAttribute("registerPassword");
            success = false;
        } else if (password.length() < User.MINIMUM_PASSWORD_LENGTH ||
                   password.length() > User.MAXIMUM_PASSWORD_LENGTH) {
            session.setAttribute("registerPasswordErr", "* Password must be between "
                    + User.MINIMUM_PASSWORD_LENGTH + " and " + User.MAXIMUM_PASSWORD_LENGTH
                    + " characters!");
            session.removeAttribute("registerPassword");
            success = false;
        } else if (confirmPassword.isEmpty()) {
            session.setAttribute("registerConfirmPasswordErr", "* ConfirmPassword cannot be empty!");
            session.removeAttribute("registerConfirmPassword");
            success = false;
        } else if (!password.equals(confirmPassword)) {
            session.setAttribute("registerConfirmPasswordErr", "* ConfirmPassword does not match Password!");
            session.removeAttribute("registerConfirmPassword");
            success = false;
        }
        
        if (email.isEmpty()) {
            session.setAttribute("registerEmailErr", "* Email cannot be empty!");
            session.removeAttribute("registerEmail");
            success = false;
        } else {
            try {
                InternetAddress test = new InternetAddress(email);
                test.validate();
            } catch (AddressException ex) {
                session.setAttribute("registerEmailErr", "* Email must be a valid e-mail address!");
                session.removeAttribute("registerEmail");
                success = false;
            }
        }
        
        if (year.isEmpty()) {
            session.setAttribute("registerYearErr", "* Year of Study cannot be empty!");
            session.removeAttribute("registerYear");
            success = false;
        } else {
            try {
                studentYear = Integer.parseInt(year);
            } catch (NumberFormatException ex) {
                session.setAttribute("registerYearErr", "* Year of Study must be an integer!");
                session.removeAttribute("registerYear");
                success = false;
            }
        }
        
        if (programCode.isEmpty()) {
            session.setAttribute("registerProgramCodeErr", "* Program Code cannot be empty!");
            session.removeAttribute("registerProgramCode");
            success = false;
        }
        
        if (programDescription.isEmpty()) {
            session.setAttribute("registerProgramDescriptionErr", "* Program Description cannot be empty!");
            session.removeAttribute("registerProgramDescription");
            success = false;
        }
        
        if (success) {
            // Remove error messages.
            session.removeAttribute("registerIdErr");
            session.removeAttribute("registerFirstNameErr");
            session.removeAttribute("registerLastNameErr");
            session.removeAttribute("registerPasswordErr");
            session.removeAttribute("registerConfirmPasswordErr");
            session.removeAttribute("registerEmailErr");
            session.removeAttribute("registerYearErr");
            session.removeAttribute("registerProgramCodeErr");
            session.removeAttribute("registerProgramDescriptionErr");
            
            // Remove the values.
            session.removeAttribute("registerId");
            session.removeAttribute("registerFirstName");
            session.removeAttribute("registerLastName");
            session.removeAttribute("registerPassword");
            session.removeAttribute("registerConfirmPassword");
            session.removeAttribute("registerEmail");
            session.removeAttribute("registerYear");
            session.removeAttribute("registerProgramCode");
            session.removeAttribute("registerProgramDescription");
            
            // Register the new student.
            try {
                Student newStudent = new Student(studentId, password, firstName,
                    lastName, email, new Date(), new Date(), 's', true,
                    programCode, programDescription, studentYear);
                
                newStudent.create();
                newStudent = Student.authenticate(studentId, password);
                
                // If we succeeded, we need to commit the changes.
                if (newStudent != null) {
                    DatabaseConnect.getDbConnection().commit();
                } else {
                    DatabaseConnect.getDbConnection().rollback();
                }
                
                session.setAttribute("user", newStudent);
            } catch (InvalidUserDataException ex) {
                WebLogger.logError(RegisterServlet.class.getName()
                        + " unable to create new student!", ex);
            } catch (DuplicateException ex) {
                WebLogger.logError(RegisterServlet.class.getName()
                        + " unable to register new student!", ex);
            } catch (NotFoundException ex) {
                WebLogger.logError(RegisterServlet.class.getName()
                        + " unable to authenticate newly created student!", ex);
            } catch (SQLException ex) {
                WebLogger.logError(RegisterServlet.class.getName()
                        + " unable to commit or rollback db changes!", ex);
            }
            
            res.sendRedirect("./dashboard.jsp");
        } else {
            res.sendRedirect("./register.jsp");
        }
    }
}
