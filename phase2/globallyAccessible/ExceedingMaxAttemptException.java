package globallyAccessible;

/**
 * Gets thrown when max attempt is exceeded during the login/menu phase.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class ExceedingMaxAttemptException extends Exception{
    public ExceedingMaxAttemptException(String message){
        super(message);
    }
}
