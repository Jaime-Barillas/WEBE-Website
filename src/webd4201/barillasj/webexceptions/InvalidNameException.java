package webd4201.barillasj.webexceptions;

/**
 * An Exception raised when an invalid name is encountered.
 *
 * @author Jaime Barillas
 * @version 0.1.0 (2019/01/20)
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class InvalidNameException extends Exception {

    /**
     * Construct an InvalidNameException with the provided message.
     *
     * @param message (String) Error message.
     */
    public InvalidNameException(String message) {
        super(message);
    }
    
    /**
     * Construct an InvalidNameException with a default message.
     */
    public InvalidNameException() {
        this("An invalid name was encountered.");
    }
}
