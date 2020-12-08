package Controllers;

import globallyAccessible.UserNotFoundException;
import useCases.OrganizerManager;

import java.util.ArrayList;

public class MessageAllAttendeeController extends MessagingController {
    private final OrganizerManager organizerManager;

    public MessageAllAttendeeController(UserController userController) {
        super(userController);
        organizerManager = new OrganizerManager(userManager);
    }

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
