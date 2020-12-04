package Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SendEventMessageController extends MessagingController {
    private ArrayList<String[]> info;

    public SendEventMessageController(UserController userController) {
        super(userController);
        info = showEnrolledSchedule();
    }

    public void sendEventMessage(int activity, String message) {
        UUID chatID = findRightChat(activity);
        messageRoomManager.sendMessage(message, chatID);
    }

    public UUID findRightChat(int activity){
        return eventManager.getConferenceChat(
                UUID.fromString(info.get(activity - 1)[0]));
    }

    public ArrayList<String[]> showEnrolledSchedule(){
        HashMap<LocalDateTime[], UUID> schedulesEnrolled = userManager.schedules();
        ArrayList<String[]> info = new ArrayList<>();
        for (UUID actID: schedulesEnrolled.values()){
            info.add(eventManager.searchEventByUUID(actID.toString()));
        }
        return info;
    }
}
