package globallyAccessible;

/**
 * Gets thrown when no event is present.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class NoEventsException extends Exception {
    public NoEventsException(String message){
        super(message);
    }
}
