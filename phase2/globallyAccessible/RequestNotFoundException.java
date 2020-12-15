package globallyAccessible;

/**
 * Gets thrown when request does not exist.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class RequestNotFoundException extends Exception {
    public RequestNotFoundException(String message){
        super(message);
    }
}
