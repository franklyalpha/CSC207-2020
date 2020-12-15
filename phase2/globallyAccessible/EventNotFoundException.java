package globallyAccessible;

/**
 * Gets thrown when event doesn't exist.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class EventNotFoundException extends Exception{
    public EventNotFoundException(String message){
        super(message);
    }
}
