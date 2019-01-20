package webd4201.barillasj;

import java.text.DecimalFormat;

/**
 * Holds grade information, both the result and weighting, for a single course.
 *
 * @author Jaime Barillas
 * @version 0.1.0
 * @since 2019-01-11
 */
public class Mark {

    //-------- Constants --------//
    /**
     * The minimum valid GPA.
     */
    public static float MINIMUM_GPA = 0.0f;

    /**
     * The maximum valid GPA.
     */
    public static float MAXIMUM_GPA = 5.0f;

    /**
     * Formatter for displaying the GPA.
     */
    public static DecimalFormat GPA = new DecimalFormat("0.0");

    //-------- Variables --------//
    /**
     * The code for the course this mark belongs to.
     */
    private String courseCode;

    /**
     * The name of the course this mark belongs to.
     */
    private String courseName;

    /**
     * The resulting mark a student got in this course.
     */
    private int result;

    /**
     * The weighting this course is worth when calculating the overall GPA.
     */
    private float gpaWeighting;

    //-------- Properties --------//
    /**
     * [Get] The code of the course this mark belongs to.
     *
     * @return (String) The course code.
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * [Set] The code of the course this mark belongs to.
     *
     * @param courseCode (String) The course code.
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * [Get] The name of the course this mark belongs to.
     *
     * @return (String) The course name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * [Set] The name of the course this mark belongs to.
     *
     * @param courseName (String) the course name.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * [Get] The resulting mark of a course.
     *
     * @return (int) The final mark for a course.
     */
    public int getResult() {
        return result;
    }

    /**
     * [Set] The resulting mark of a course.
     *
     * @param result (int) The final mark for a course.
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     * [Get] The weighting for this course when calculating the final GPA.
     *
     * @return (float) The weighting for this course mark.
     */
    public float getGpaWeighting() {
        return gpaWeighting;
    }

    /**
     * [Set] The weighting for this course when calculating the final GPA.
     *
     * @param gpaWeighting (float) The weighting for this course mark.
     */
    public void setGpaWeighting(float gpaWeighting) {
        this.gpaWeighting = gpaWeighting;
    }

    //-------- Constructor(s) --------//
    /**
     * Construct a Mark using the provided data.
     *
     * @param courseCode   The code of the course this mark belongs to.
     * @param courseName   The name of the course this mark belongs to.
     * @param result       The resulting mark the student achieved.
     * @param gpaWeighting The weighting for this course mark when calculating
     *                     the final GPA.
     */
    public Mark(String courseCode, String courseName, int result,
            float gpaWeighting) {
        // Use of initialize method solves netbeans "Override-able method call"
        // warning.
        this.setAttributes(courseCode, courseName, result, gpaWeighting);
    }

    //-------- Methods --------//
    /**
     * Initializes the attributes of the Mark class. Avoids the "Override-able
     * method call" warning.
     *
     * @param courseCode   The code of the course this mark belongs to.
     * @param courseName   The name of the course this mark belongs to.
     * @param result       The resulting mark the student achieved.
     * @param gpaWeighting The weighting for this course mark when calculating
     *                     the final GPA.
     */
    private void setAttributes(String courseCode, String courseName, int result,
            float gpaWeighting) {
        this.setCourseCode(courseCode);
        this.setCourseName(courseName);
        this.setResult(result);
        this.setGpaWeighting(gpaWeighting);
    }

    /**
     * Return a String representation of Mark info. Generates a String of the
     * format:
     * <pre>
     * [course code] [course name] [result] [gpa weighting]
     * </pre>
     *
     * @return (String) of Mark info.
     */
    @Override
    public String toString() {
        return String.format("%s %-35s %-3d %s", this.getCourseCode(),
                this.getCourseName(), this.getResult(),
                GPA.format(this.getGpaWeighting()));
    }
}
