package webd4201.barillasj;

import java.sql.Connection;
import java.time.Instant;
import java.util.Date;
import java.util.Vector;

import webd4201.barillasj.db.StudentDA;
import webd4201.barillasj.webexceptions.DuplicateException;
import webd4201.barillasj.webexceptions.InvalidUserDataException;
import webd4201.barillasj.webexceptions.NotFoundException;

/**
 * A User type to represent the Student.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-01-11
 */
public class Student extends User {

    //-------- Constants --------//
    /**
     * Default program code.
     */
    public static final String DEFAULT_PROGRAM_CODE = "UNDC";

    /**
     * Default program description.
     */
    public static final String DEFAULT_PROGRAM_DESCRIPTION = "Undeclared";

    /**
     * Default year of study.
     */
    public static final int DEFAULT_YEAR = 1;

    //-------- Variables --------//
    /**
     * The code for the program the student is enrolled in.
     */
    private String programCode;

    /**
     * The description (name) of the program the student is enrolled in.
     */
    private String programDescription;

    /**
     * The year of study the student currently is in.
     */
    private int year;

    /**
     * The Marks for each of the courses the student is enrolled in.
     */
    private Vector<Mark> marks;

    //-------- Properties --------//
    /**
     * [Get] The code of the program the student is enrolled in.
     *
     * @return (String) The program code.
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * [Set] The code of the program the student is enrolled in.
     *
     * @param programCode (String) The program code.
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    /**
     * [Get] The description (name) of the program the student is enrolled in.
     *
     * @return (String) The program description.
     */
    public String getProgramDescription() {
        return programDescription;
    }

    /**
     * [Set] The description (name) of the program the student is enrolled in.
     *
     * @param programDescription (String) The program description.
     */
    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    /**
     * [Get] The year of study the student is in.
     *
     * @return (int) The year of study.
     */
    public int getYear() {
        return year;
    }

    /**
     * [Set] The year of study the student is in.
     *
     * @param year (int) The year of study.
     */
    public void setYear(int year) {
        this.year = year;
    }
    
    /**
     * [Get] The Marks for each of the courses this student is enrolled in.
     *
     * @return (Vector&lt;Mark&gt;) Marks for each of the courses the student is in.
     */
    public Vector<Mark> getMarks() {
        return marks;
    }
    
    /**
     * [Set] The Marks for each of the courses this student is enrolled in.
     *
     * @param marks (Vector&lt;Mark&gt;) Marks for each of the courses the student is in.
     */
    public void setMarks(Vector<Mark> marks) {
        this.marks = marks;
    }

    //-------- Constructor(s) --------//
    /**
     * Construct a Student with the provided data.
     *
     * @param id                 Id number used to identify the user.
     * @param password           Password for the user's account.
     * @param firstName          User's given name.
     * @param lastName           User's surname.
     * @param emailAddress       User's email address.
     * @param lastAccess         Date that the user last accessed the system.
     * @param enrollDate         Date that the user enrolled in the system.
     * @param enabled            Whether the user is enabled (can access the
     *                           system).
     * @param type               The type of the user.
     * @param programCode        The code of the program the student is enrolled
     *                           in.
     * @param programDescription The description of the program the student is
     *                           enrolled in.
     * @param year               The year of study the student is in.
     * @param marks              The marks for each of the courses the student
     *                           is in.
     *
     * @throws InvalidUserDataException
     */
    public Student(long id, String password, String firstName, String lastName,
            String emailAddress, Date lastAccess, Date enrollDate,
            char type, boolean enabled, String programCode,
            String programDescription, int year, Vector<Mark> marks)
            throws InvalidUserDataException {
        // Call the parent constructor
        super(id, password, firstName, lastName, emailAddress, lastAccess,
                enrollDate, type, enabled);
        // Avoids 'override-able method in constructor' warning.
        this.setAttributes(programCode, programDescription, year, marks);
    }

    /**
     * Construct a Student with the provided data and no Marks.
     *
     * @param id                 Id number used to identify the user.
     * @param password           Password for the user's account.
     * @param firstName          User's given name.
     * @param lastName           User's surname.
     * @param emailAddress       User's email address.
     * @param lastAccess         Date that the user last accessed the system.
     * @param enrollDate         Date that the user enrolled in the system.
     * @param enabled            Whether the user is enabled (can access the
     *                           system).
     * @param type               The type of the user.
     * @param programCode        The code of the program the student is enrolled
     *                           in.
     * @param programDescription The description of the program the student is
     *                           enrolled in.
     * @param year               The year of study the student is in.
     *
     * @throws InvalidUserDataException
     */
    public Student(long id, String password, String firstName, String lastName,
            String emailAddress, Date lastAccess, Date enrollDate,
            char type, boolean enabled, String programCode,
            String programDescription, int year)
            throws InvalidUserDataException {
        // Call other constructor.
        this(id, password, firstName, lastName, emailAddress, lastAccess,
                enrollDate, type, enabled, programCode, programDescription,
                year, new Vector<Mark>());
    }

    /**
     * Construct a Student using default values.
     *
     * @throws InvalidUserDataException
     */
    public Student() throws InvalidUserDataException {
        this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS,
                Date.from(Instant.now()), Date.from(Instant.now()),
                DEFAULT_TYPE, DEFAULT_ENABLED_STATUS, DEFAULT_PROGRAM_CODE,
                DEFAULT_PROGRAM_DESCRIPTION, DEFAULT_YEAR);
    }

    //-------- Methods --------//
    /**
     * Initialize each of the attributes of this class, avoids "Override-able
     * method call" warning.
     *
     * @param programCode        The code of the program the student is enrolled
     *                           in.
     * @param programDescription The description of the program the student is
     *                           enrolled in.
     * @param year               The year of study the student is in.
     * @param marks              The marks for each of the courses the student
     *                           is in.
     */
    private void setAttributes(String programCode, String programDescription,
            int year, Vector<Mark> marks) {
        this.setProgramCode(programCode);
        this.setProgramDescription(programDescription);
        this.setYear(year);
        this.setMarks(marks);
    }

    /**
     * Initialize the database queries.
     *
     * @param dbConnection The database to use.
     */
    public static void initialize(Connection dbConnection) {
        StudentDA.initialize(dbConnection);
    }
    
    /**
     * Close the database queries.
     */
    public static void terminate() {
        StudentDA.terminate();
    }
    
    /**
     * Retrieve a student from the database.
     *
     * @param studentId (long) The student to retrieve.
     * @return (Student) The requested student.
     *
     * @throws NotFoundException If the student does not exist within the
     * database.
     */
    public static Student retrieve(long studentId) throws NotFoundException {
        return StudentDA.retrieve(studentId);
    }
    
    /**
     * Add this student to the database.
     *
     * @return (boolean) true if the creation was successful, false otherwise.
     * @throws DuplicateException If student already exists within the database.
     */
    public boolean create() throws DuplicateException {
        return StudentDA.create(this);
    }
    
    /**
     * Update this student's info within the database.
     *
     * @return (int) The number of rows affected.
     * @throws NotFoundException If the student does not exist within the
     * database.
     */
    public int update() throws NotFoundException {
        return StudentDA.update(this);
    }
    
    /**
     * Delete this student from the database.
     *
     * @return (int) The number of rows affected.
     * @throws NotFoundException If the student does not exist within the
     * database.
     */
    public int delete() throws NotFoundException {
        return StudentDA.delete(this);
    }
    
    /**
     * Authenticates a student with the given id and password. If such a student
     * exists, then the corresponding Student is returned.
     *
     * @param studentId       The id of the student to authenticate.
     * @param studentPassword The student's password.
     * @return The authenticated Student.
     * @throws NotFoundException If the student id + password combo is incorrect.
     */
    public static Student authenticate(long studentId, String studentPassword)
            throws NotFoundException {
        return StudentDA.authenticate(studentId, studentPassword);
    }
    
    public static Student updatePassword(long studentId, String newPassword) {
        return StudentDA.updatePassword(studentId, newPassword);
    }
    
    /**
     * The human friendly name for this User type.
     *
     * @return
     */
    @Override
    public String getTypeForDisplay() {
        return "Student";
    }
    
    /**
     * Display this students info to the console.
     */
    public void displayToConsole() {
        System.out.println(toString());
    }

    /**
     * Returns a string representation of Student info. Generates a string of
     * the format:
     * <pre>
     * Student Info for:
     *      [first name] [last name] ([id])
     *      Currently enrolled in [year] of "[course description]" ([course code])
     *      Enrolled: [enroll date]
     * </pre>
     * 
     * @return (String) Of Student info.
     */
    @Override
    public String toString() {
        String format = "Student Info for:\n"
                + "     %s %s (%d)\n"
                + "     Currently enrolled in %s year of \"%s\" (%s)\n"
                + "     Enrolled: %s";
        String yearSuffix;

        switch (this.year) {
            case 1:
                yearSuffix = "1st";
                break;
            case 2:
                yearSuffix = "2nd";
                break;
            case 3:
                yearSuffix = "3rd";
                break;
            default:
                yearSuffix = this.year + "th";
        }

        return String.format(format, this.getFirstName(), this.getLastName(),
                this.getId(), yearSuffix, this.getProgramDescription(),
                this.getProgramCode(), this.getEnrollDate().toString());
    }
}
