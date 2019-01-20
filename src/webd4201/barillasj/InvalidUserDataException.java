package webd4201.barillasj;

/**
 * An Exception raised when invalid user data is encountered.
 *
 * @author Jaime Barillas
 * @version 0.1.0 (2019/01/20)
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class InvalidUserDataException extends Exception {
    
    /**
     * Construct an InvalidUserDataException with the provided message.
     *
     * @param message (String) Error message.
     */
    public InvalidUserDataException(String message) {
        super(message);
    }
    
    /**
     * Construct an InvalidUserDataException with a default message.
     */
    public InvalidUserDataException() {
        this("Invalid User data was encountered.");
    }
}
