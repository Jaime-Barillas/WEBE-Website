package webd4201.barillasj.webexceptions;

/**
 * An Exception raised when an invalid id encountered.
 *
 * @author Jaime Barillas
 * @version 0.1.0 (2019/01/20)
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class InvalidIdException extends Exception {

    /**
     * Construct an InvalidIdException with the provided message.
     *
     * @param message (String) Error message.
     */
    public InvalidIdException(String message) {
        super(message);
    }
    
    /**
     * Construct an InvalidIdException with a default message.
     */
    public InvalidIdException() {
        this("An invalid id was encountered.");
    }
}
