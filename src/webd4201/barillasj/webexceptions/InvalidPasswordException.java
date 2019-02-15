package webd4201.barillasj.webexceptions;

/**
 * An Exception raised when an invalid password is encountered.
 *
 * @author Jaime Barillas
 * @version 0.1.0 (2019/01/20)
 * @since 0.1.0
 */
@SuppressWarnings("serial")
public class InvalidPasswordException extends Exception {

    /**
     * Construct an InvalidPasswordException with the provided message.
     *
     * @param message (String) Error message.
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
    
    /**
     * Construct an InvalidPasswordException with a default message
     */
    public InvalidPasswordException() {
        this("An invalid password was encountered.");
    }

}
