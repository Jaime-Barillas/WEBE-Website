package webd4201.barillasj;

/**
 * Contains constants for use, as well a single method.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-01-11
 */
public interface CollegeInterface {

    //-------- Constants --------//
    /**
     * The name for the college.
     */
    public static final String COLLEGE_NAME = "Durham College";

    /**
     * The phone number for the college.
     */
    public static final String PHONE_NUMBER = "(905)721-2000";

    /**
     * The lowest allowed id.
     */
    public static final long MINIMUM_ID_NUMBER = 100000000;

    /**
     * The largest allowed id.
     */
    public static final long MAXIMUM_ID_NUMBER = 999999999;

    //-------- Methods --------//
    /**
     * Returns a human readable string for display.
     *
     * @return (String) Human readable representation of User type.
     */
    public String getTypeForDisplay();
}
