package webd4201.barillasj;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import webd4201.barillasj.db.UserDA;
import webd4201.barillasj.webexceptions.DuplicateException;

import webd4201.barillasj.webexceptions.InvalidIdException;
import webd4201.barillasj.webexceptions.InvalidNameException;
import webd4201.barillasj.webexceptions.InvalidPasswordException;
import webd4201.barillasj.webexceptions.InvalidUserDataException;
import webd4201.barillasj.webexceptions.NotFoundException;

/**
 * Holds the base information for each user.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-01-11
 */
public class User implements CollegeInterface {

    //-------- Constants --------//
    /**
     * Id number for the user. Defaults to 100123456.
     */
    protected static final long DEFAULT_ID = 100123456;

    /**
     * Each user has an alpha-numeric password. Defaults to password.
     */
    protected static final String DEFAULT_PASSWORD = "password";
   
    /**
     * The minimum length of a password.
     */
    public static final byte MINIMUM_PASSWORD_LENGTH = 8;
    
    /**
     * The maximum length of a password.
     */
    public static final byte MAXIMUM_PASSWORD_LENGTH = 20;
    
    /**
     * The user's first name. Defaults to John.
     */
    protected static final String DEFAULT_FIRST_NAME = "John";

    /**
     * The user's last name. Defaults to Doe.
     */
    protected static final String DEFAULT_LAST_NAME = "Doe";

    /**
     * The user's email address. Defaults to john.doe@dcmail.com.
     */
    protected static final String DEFAULT_EMAIL_ADDRESS = "john.doe@dcmail.com";

    /**
     * Determines whether the user is enabled or disabled. Defaults to enabled
     * (true).
     */
    protected static final boolean DEFAULT_ENABLED_STATUS = true;

    /**
     * The type of the sure. e.g. s for student. Defaults to s.
     */
    protected static final char DEFAULT_TYPE = 's';

    /**
     * The length an id number must be.
     */
    protected static final byte ID_NUMBER_LENGTH = 9;

    /**
     * Formatter for the date in the current local (Canada).
     */
    protected static final DateFormat DF = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA);

    //-------- Variables --------//
    /**
     * The user's id.
     */
    private long id;

    /**
     * The user's password.
     */
    private String password;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's email address.
     */
    private String emailAddress;

    /**
     * The date that the user last accessed the system.
     */
    private Date lastAccess;

    /**
     * The date the user enrolled into the college.
     */
    private Date enrollDate;

    /**
     * Determines if the user is enabled and can access the system.
     */
    private boolean enabled;

    /**
     * The user's type, f for faculty, etc.
     */
    private char type;

    //-------- Properties --------//
    /**
     * [Get] The user's id.
     *
     * @return (long) The user's id.
     */
    public long getId() {
        return id;
    }

    /**
     * [Set] The user's id.
     *
     * @param id (long) The new id for this User.
     * 
     * @throws InvalidIdException
     */
    public void setId(long id) throws InvalidIdException {
        if (this.verifyId(id)) {
            this.id = id;
        } else {
            throw new InvalidIdException("The id: " + id
                    + " is not within the range " + MINIMUM_ID_NUMBER + " to "
                    + MAXIMUM_ID_NUMBER + ".");
        }
    }

    /**
     * [Get] The user's password.
     *
     * @return (String) The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * [Set] The user's password.
     *
     * @param password (String)
     *
     * @throws InvalidPasswordException
     */
    public void setPassword(String password) throws InvalidPasswordException {
        if (password == null) {
            throw new InvalidPasswordException("The password cannot be null.");
        // NOTE: pass hash exceeds password length so the check is disabled
        } else if (false && !verifyPassword(password)) {
            throw new InvalidPasswordException("The password must be between "
                    + MINIMUM_PASSWORD_LENGTH + " and "
                    + MAXIMUM_PASSWORD_LENGTH + " characters long.");
        } else {
            this.password = password;
        }
    }

    /**
     * [Get] The user's first name.
     *
     * @return (String) The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * [Set] The user's first name.
     *
     * @param firstName (String) The user's first name.
     *
     * @throws InvalidNameException
     */
    public void setFirstName(String firstName) throws InvalidNameException {
        if (firstName == null) {
            throw new InvalidNameException("The first name cannot be null.");
        } else if (!verifyName(firstName)) {
            throw new InvalidNameException("The first name cannot be empty or a number.");
        } else {
            this.firstName = firstName;
        }
    }

    /**
     * [Get] The user's last name.
     *
     * @return (String) The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * [Set] The user's last name.
     *
     * @param lastName (String) The user's last name.
     *
     * @throws InvalidNameException
     */
    public void setLastName(String lastName) throws InvalidNameException {
        if (lastName == null) {
            throw new InvalidNameException("The last name cannot be null.");
        } else if (!verifyName(lastName)) {
            throw new InvalidNameException("The last name cannot be empty or a number.");
        } else {
            this.lastName = lastName;
        }
    }

    /**
     * [Get] The user's full name.
     *
     * @return (String) The user's first and last name.
     */
    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    /**
     * [Get] The user's email address.
     *
     * @return (String) The user's email address.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * [Set] The user's email address.
     *
     * @param emailAddress (String) The user's email address.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * [Get] The date of the user's last access to the system.
     *
     * @return (Date) The user's last access.
     */
    public Date getLastAccess() {
        return lastAccess;
    }

    /**
     * [Set] The date the user last accessed the system.
     *
     * @param lastAccess (Date) The user's last access date.
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    /**
     * [Get] The user's date of enrollment.
     *
     * @return (Date) The day of enrollment.
     */
    public Date getEnrollDate() {
        return enrollDate;
    }

    /**
     * [Set] The user's date of enrollment.
     *
     * @param enrollDate (Date) The day of enrollment.
     */
    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    /**
     * [Get] Whether the user is enabled, can access the system.
     *
     * @return (boolean) Whether the user is enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * [Set] Whether the user is enabled, can access the system.
     *
     * @param enabled (boolean) Whether the user is enabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * [Get] The type of user, Student, Faculty, etc.
     *
     * @return (char) The user's type.
     */
    public char getType() {
        return type;
    }

    /**
     * [Set] The type of user, Student, Faculty, etc.
     *
     * @param type (char) The user's type.
     */
    public void setType(char type) {
        this.type = type;
    }

    //-------- Constructor(s) --------//
    /**
     * Construct a User with the specified info.
     *
     * @param id           Id number used to identify the user.
     * @param password     Password for the user's account.
     * @param firstName    User's given name.
     * @param lastName     User's surname.
     * @param emailAddress User's email address.
     * @param lastAccess   Date that the user last accessed the system.
     * @param enrollDate   Date that the user enrolled in the system.
     * @param enabled      Whether the user is enabled (can access the system).
     * @param type         The type of the user.
     *
     * @throws InvalidUserDataException
     */
    public User(long id, String password, String firstName, String lastName,
            String emailAddress, Date lastAccess, Date enrollDate,
            char type, boolean enabled) throws InvalidUserDataException  {
        // Initialize attributes within a private method to avoid
        // "override-able method call" warning.
        try {
            this.setAttributes(id, password, firstName, lastName, emailAddress,
                    lastAccess, enrollDate, type, enabled);
        } catch (InvalidIdException | InvalidPasswordException | InvalidNameException ex) {
            throw new InvalidUserDataException(ex.getMessage());
        }
    }

    /**
     * Construct a User using default values.
     *
     * @throws InvalidUserDataException
     */
    public User() throws InvalidUserDataException {
        this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS,
                Date.from(Instant.now()), Date.from(Instant.now()),
                DEFAULT_TYPE, DEFAULT_ENABLED_STATUS);
    }

    //-------- Methods --------//
    /**
     * Initialize each of the attributes of this class. Avoids "override-able
     * method call" warning.
     *
     * @param id           Id number used to identify the user.
     * @param password     Password for the user's account.
     * @param firstName    User's given name.
     * @param lastName     User's surname.
     * @param emailAddress User's email address.
     * @param lastAccess   Date that the user last accessed the system.
     * @param enrollDate   Date that the user enrolled in the system.
     * @param enabled      Whether the user is enabled (can access the system).
     * @param type         The type of the user.
     * 
     * @throws InvalidIdException
     * @throws InvalidPasswordException
     */
    private void setAttributes(long id, String password, String firstName,
            String lastName, String emailAddress, Date lastAccess,
            Date enrollDate, char type, boolean enabled)
            throws InvalidIdException, InvalidPasswordException, InvalidNameException {
        this.setId(id);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmailAddress(emailAddress);
        this.setLastAccess(lastAccess);
        this.setEnrollDate(enrollDate);
        this.setEnabled(enabled);
        this.setType(type);
    }

    /**
     * Verify that the provided id is within the acceptable range.
     *
     * @param idToTest The id to verify.
     * @return (boolean) true if the id is valid, false otherwise.
     */
    public static boolean verifyId(long idToTest) {
        return idToTest >= CollegeInterface.MINIMUM_ID_NUMBER
                && idToTest <= CollegeInterface.MAXIMUM_ID_NUMBER;
    }
    
    /**
     * Verify that the provided password is an appropriate length.
     *
     * @param passwordToTest The password to verify.
     * @return (boolean) true if the password is valid, false otherwise.
     */
    public static boolean verifyPassword(String passwordToTest) {
        return passwordToTest.length() >= MINIMUM_PASSWORD_LENGTH
                && passwordToTest.length() <= MAXIMUM_PASSWORD_LENGTH;
    }
    
    /**
     * Verify that the provided password is not an empty string or a number.
     *
     * @param nameToTest The name to verify.
     * @return (boolean) true if the name is valid, false otherwise.
     */
    public static boolean verifyName(String nameToTest) {
        /* When is a name a number? When is a number a name?
         * If the whole name is a valid number then it will be a number, if it
         * is not a valid number then it will be considered a valid name e.g.
         * 123.56 is a number, but 123.26.4 is a name.
         */
        boolean validName = false;
        
        try {
            Double.parseDouble(nameToTest);
        } catch (NullPointerException | NumberFormatException ex) {
            validName = true;
        }
        
        return validName;
    }

    /**
     * Returns the user type as a String.
     * @return The type of the user.
     */
    @Override
    public String getTypeForDisplay() {
        return "User";
    }
    
    /**
     * Generates a string of the format:
     * <pre>
     * User Info for: [id]
     *      Name: [full name]
     *      Created on: [enroll date]
     *      Last access: [last access date]
     * </pre>
     *
     * @return (String) Of user info.
     */
    @Override
    public String toString() {
        String format = "User Info for: %d\n"
                + "     Name: %s\n"
                + "     Created on: %s\n"
                + "     Last access: %s";

        return String.format(format, this.getId(), this.getFullName(),
                DF.format(this.getEnrollDate()), DF.format(this.getLastAccess()));
    }

    /**
     * Print the string generated by toString() to System.out.
     */
    public void dump() {
        System.out.println(this.toString());
    }
    
    /**
     * Create a new user in the database.
     *
     * @param newUser The user to add to the database.
     * @return true on success, false on failure.
     *
     * @throws DuplicateException If another user with the same id exists.
     */
    public static boolean create(User newUser) throws DuplicateException {
        return UserDA.create(newUser);
    }
    
    /**
     * Retrieve a user with the specified id.
     *
     * @param userId The id of the user.
     * @return The desired user.
     *
     * @throws NotFoundException If no user with the id exists.
     */
    public static User retrieve(long userId) throws NotFoundException {
        return UserDA.retrieve(userId);
    }
    
    /**
     * Update the details for a user in the database.
     *
     * @param userToUpdate The user with the updated details.
     * @return The number of rows updated.
     *
     * @throws NotFoundException If no user with the same id is found.
     */
    public static int update(User userToUpdate) throws NotFoundException {
        return UserDA.update(userToUpdate);
    }
    
    /**
     * Delete a user from the database.
     *
     * @param userToDelete The user to delete.
     * @return The number of affected rows.
     * @throws NotFoundException If the user is not found within the database.
     */
    public static int delete(User userToDelete) throws NotFoundException {
        return UserDA.delete(userToDelete);
    }
}
