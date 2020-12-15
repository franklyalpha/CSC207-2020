package globallyAccessible;

/**
 * Gets thrown when creating the same user.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String message){
        super(message);
    }
}
