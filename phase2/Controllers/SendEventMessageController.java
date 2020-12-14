package Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Controller managing methods dealing with sending messages to an event groupchat.
 */
public class SendEventMessageController extends MessagingController {
    private final ArrayList<String[]> info;

    /**
     * Constructor for an instance of <code>SendEventMessageController</code>
     * @param userController Instance of <code>UserController</code>.
     */
    public SendEventMessageController(UserController userController) {
        super(userController);
        info = showEnrolledSchedule();
    }

    /**
     * Sends the specified message to the groupchat associated with the specified event.
     * @param activity Integer identifying which event to message.
     * @param message String containing the message to be sent.
     */
    public void sendEventMessage(int activity, String message) {
        UUID chatID = findRightChat(activity);
        String result = userManager.getUsername() + ": " + message;
        messageRoomManager.sendMessage(result, chatID);
    }

    /**
     * Finds the UUID of the chatroom for the specified event.
     * @param activity Integer identifying which event to find the UUID of.
     * @return Returns the UUID of the groupchat associated with the specified event.
     */
    public UUID findRightChat(int activity){
        return eventManager.getEventChat(
                UUID.fromString(info.get(activity - 1)[0]));
    }

    /**
     * Shows the schedule of events for which the user has been enrolled in.
     * @return ArrayList of strings representing information about events.
     */
    public ArrayList<String[]> showEnrolledSchedule(){
        HashMap<LocalDateTime[], UUID> schedulesEnrolled = userManager.schedules();
        ArrayList<String[]> info = new ArrayList<>();
        for (UUID actID: schedulesEnrolled.values()){
            info.add(eventManager.searchEventByUUID(actID.toString()));
        }
        return info;
    }
}
