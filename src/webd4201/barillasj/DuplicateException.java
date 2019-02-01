package webd4201.barillasj;

/**
 * Raised when there is an attempt insert a duplicate record.
 *
 * @author Jaime Barillas
 * @version 0.1.0 (2019/01/21)
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class DuplicateException extends Exception {
    
    /**
     * Construct a DuplicateException with the provided error message.
     *
     * @param message (String) The error message.
     */
    public DuplicateException(String message) {
        super(message);
    }
    
    /**
     * Construct a DuplicateException with a default error message.
     */
    public DuplicateException() {
        this("The record to insert already exists within the database.");
    }
}
