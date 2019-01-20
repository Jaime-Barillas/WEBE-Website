package webd4201.barillasj;

import java.time.Instant;
import java.util.Date;

/**
 * A User type to represent college faculty.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-01-11
 */
public class Faculty extends User {

    //-------- Constants --------//
    /**
     * Default school code: SET for School of Engineering & Technology.
     */
    public static final String DEFAULT_SCHOOL_CODE = "SET";

    /**
     * Default description of school.
     */
    public static final String DEFAULT_SCHOOL_DESCRIPTION = "School of Engineering & Technology";

    /**
     * Default office room number: H-140.
     */
    public static final String DEFAULT_OFFICE = "H-140";

    /**
     * Default phone extension: 1234
     */
    public static final int DEFAULT_PHONE_EXTENSION = 1234;

    //-------- Variables --------//
    /**
     * Holds the short form code for the school of discipline.
     */
    private String schoolCode;

    /**
     * The full name of the school.
     */
    private String schoolDescription;

    /**
     * The Faculty's office room number.
     */
    private String office;

    /**
     * The phone number extension for this Faculty member.
     */
    private int extension;

    //-------- Properties --------//
    /**
     * [Get] The school code.
     *
     * @return (String) The school code.
     */
    public String getSchoolCode() {
        return schoolCode;
    }

    /**
     * [Set] The school code.
     *
     * @param schoolCode (String) The short form code for the school.
     */
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    /**
     * [Get] The full name of the school.
     *
     * @return (String) The name of the school.
     */
    public String getSchoolDescription() {
        return schoolDescription;
    }

    /**
     * [Set] The full name of the school.
     *
     * @param schoolDescription (String) The name of the school.
     */
    public void setSchoolDescription(String schoolDescription) {
        this.schoolDescription = schoolDescription;
    }

    /**
     * [Get] The Faculty's office room number.
     *
     * @return (String) The office room number.
     */
    public String getOffice() {
        return this.office;
    }

    /**
     * [Set] The room number for the Faculty's office.
     *
     * @param office (String) The room number for this Faculty's office.
     */
    public void setOffice(String office) {
        this.office = office;
    }

    /**
     * [Get] The Faculty's phone extension.
     *
     * @return (int) Phone extension for this Faculty member.
     */
    public int getExtension() {
        return extension;
    }

    /**
     * [Set] Phone number extension for the Faculty.
     *
     * @param extension (int) The phone extension for this Faculty member.
     */
    public void setExtension(int extension) {
        this.extension = extension;
    }

    //-------- Constructor(s) -------//
    /**
     * Construct a Faculty member with the provided info.
     *
     * @param id                Id number for this Faculty.
     * @param password          Password for this Faculty.
     * @param firstName         The Faculty's first name.
     * @param lastName          The Faculty's last name.
     * @param emailAddress      The Faculty's email address.
     * @param lastAccess        The date this Faculty last accessed the system.
     * @param enrollDate        The date this Faculty enrolled in the system.
     * @param enabled           Whether this Faculty can access the system.
     * @param type              The type code for this Faculty.
     * @param schoolCode        The code for the school this Faculty belongs to.
     * @param schoolDescription The school this Faculty belongs to.
     * @param office            The office room number for this Faculty.
     * @param extension         The phone extension for this Faculty.
     *
     * @throws InvalidUserDataException
     */
    public Faculty(long id, String password, String firstName, String lastName,
            String emailAddress, Date lastAccess, Date enrollDate,
            boolean enabled, char type, String schoolCode,
            String schoolDescription, String office, int extension)
            throws InvalidUserDataException {
        // Initialize attributes that belong to the superclass.
        super(id, password, firstName, lastName, emailAddress, lastAccess,
                enrollDate, enabled, type);

        // Initialize the rest of the attributes within a private method to
        // avoid "override-able method call in constructor" warning.
        this.setAttributes(schoolCode, schoolDescription, office, extension);
    }

    /**
     * Construct a Faculty using default values.
     *
     * @throws InvalidIdException
     * @throws InvalidPasswordException
     */
    public Faculty() throws InvalidUserDataException {
        this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS,
                Date.from(Instant.now()), Date.from(Instant.now()),
                DEFAULT_ENABLED_STATUS, DEFAULT_TYPE, DEFAULT_SCHOOL_CODE,
                DEFAULT_SCHOOL_DESCRIPTION, DEFAULT_OFFICE,
                DEFAULT_PHONE_EXTENSION);
    }

    //-------- Methods --------//
    /**
     * Initialize attributes that belong to a Faculty.
     *
     * @param schoolCode        The short form code for a school.
     * @param schoolDescription The name of the school.
     * @param office            The office room number for the Faculty.
     * @param extension         The phone extension for the Faculty.
     */
    private void setAttributes(String schoolCode, String schoolDescription,
            String office, int extension) {
        this.setSchoolCode(schoolCode);
        this.setSchoolDescription(schoolDescription);
        this.setOffice(office);
        this.setExtension(extension);
    }

    /**
     * The human friendly name for this User type.
     *
     * @return (String) The name for this type: "Faculty".
     */
    @Override
    public String getTypeForDisplay() {
        return "Faculty";
    }

    /**
     * Returns a string representation of faculty info. Generates a string of
     * the format:
     * <pre>
     * Faculty Info for: [id]
     *      Name: [full name]
     *      Created on: [enroll date]
     *      Last access: [last access date]
     *      [school name] ([school code])
     *      Office: [office room number]
     *      [phone number] x[extension]
     * </pre>
     *
     * @return (String) Of Faculty info.
     */
    @Override
    public String toString() {
        // We will modify the string from the super class, adding to it the
        // attributes specific to a Faculty.
        String format = super.toString();
        format += "\n     %s (%s)\n"
                + "     Office: %s"
                + "      %s x%d";

        format = format.replaceAll("User", this.getTypeForDisplay());
        return String.format(format, this.getSchoolDescription(),
                this.getSchoolCode(), this.getOffice(), PHONE_NUMBER,
                this.getExtension());
    }
}
