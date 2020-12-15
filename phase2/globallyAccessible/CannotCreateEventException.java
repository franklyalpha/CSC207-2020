package globallyAccessible;

/**
 * Gets thrown when error occurs when creating event.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class CannotCreateEventException extends Exception{
    public CannotCreateEventException(String message){
        super(message);
    }
}
