package globallyAccessible;

/**
 * Gets thrown when user does not exist.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message){
        super(message);
    }
}
