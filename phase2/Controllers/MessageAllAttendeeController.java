package Controllers;

import globallyAccessible.UserNotFoundException;
import useCases.OrganizerManager;

import java.util.ArrayList;

/**
 * Responsible for sending a message to all <>Attendee</> registered in the system.
 */
public class MessageAllAttendeeController extends MessagingController {
    /**
     * An instance of <>OrganizerManager</>.
     */
    private final OrganizerManager organizerManager;

    /**
     * the constructor of this controller class.
     * @param userController An instance of <>UserController</>.
     */
    public MessageAllAttendeeController(UserController userController) {
        super(userController);
        organizerManager = new OrganizerManager(userManager);
    }

    /**
     * Responsible for updating all <>Attendee</> with given <>message</>.
     * @param message a <>String</> the <>User</> wants to send to all <>Attendee</>.
     */
    public void messageAllAttendee(String message) {
        try{
            ArrayList<String> attendeeName = organizerManager.allAttendee();
            for (String attendee : attendeeName){
                send(attendee, message);
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
