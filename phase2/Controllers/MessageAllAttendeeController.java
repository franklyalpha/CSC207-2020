package Controllers;

import globallyAccessible.UserNotFoundException;

import java.util.ArrayList;

public class MessageAllAttendeeController extends MessagingController {

    public MessageAllAttendeeController(UserController userController) {
        super(userController);
    }

    public void messageAllAttendee(String message) {
        try{
            ArrayList<String> attendeeName = userManager.allAttendee();
            for (String attendee : attendeeName){
                send(attendee, message);
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
