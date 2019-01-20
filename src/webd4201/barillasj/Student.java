package webd4201.barillasj;

import java.time.Instant;
import java.util.Date;
import java.util.Vector;

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
            boolean enabled, char type, String programCode,
            String programDescription, int year, Vector<Mark> marks)
            throws InvalidUserDataException {
        // Call the parent constructor
        super(id, password, firstName, lastName, emailAddress, lastAccess,
                enrollDate, enabled, type);
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
            boolean enabled, char type, String programCode,
            String programDescription, int year)
            throws InvalidUserDataException {
        // Call other constructor.
        this(id, password, firstName, lastName, emailAddress, lastAccess,
                enrollDate, enabled, type, programCode, programDescription,
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
                DEFAULT_ENABLED_STATUS, DEFAULT_TYPE, DEFAULT_PROGRAM_CODE,
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
     * The human friendly name for this User type.
     *
     * @return
     */
    @Override
    public String getTypeForDisplay() {
        return "Student";
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
