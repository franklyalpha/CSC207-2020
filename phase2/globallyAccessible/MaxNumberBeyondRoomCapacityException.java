package globallyAccessible;

/**
 * Gets thrown when the number of attendees exceeds the capacity.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class MaxNumberBeyondRoomCapacityException extends Exception{
    public MaxNumberBeyondRoomCapacityException(String message){
        super(message);
    }
}
