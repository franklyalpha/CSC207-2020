package globallyAccessible;

/**
 * Gets thrown when event type does not exist.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class WrongEventTypeException extends Exception {
    public WrongEventTypeException(String message){
        super(message);
    }
}
