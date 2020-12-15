package globallyAccessible;

/**
 * Gets thrown when error occurs when serializing.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class CannotSerializeException extends Exception{
    public CannotSerializeException(String message){
        super(message);
    }
}
