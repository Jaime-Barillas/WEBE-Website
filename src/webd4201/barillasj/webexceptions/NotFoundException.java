package webd4201.barillasj.webexceptions;

/**
 * Raised when a record is not found within the system's database.
 *
 * @author Jaime Barillas
 * @version 0.1.0 (2019/01/21)
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class NotFoundException extends Exception {

    /**
     * Construct a NotFoundException with the provided error message.
     *
     * @param message (String) Error message.
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Construct a NotFoundException with a default message.
     */
    public NotFoundException() {
        this("A record was not found within the database.");
    }
    
}
