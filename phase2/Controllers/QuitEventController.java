package Controllers;

import globallyAccessible.EventNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class QuitEventController extends EventController {
    private ArrayList<String> userName;

    public QuitEventController(UserController userController) {
        super(userController);
        userName = new ArrayList<>();
        userName.add(userManager.currentUsername());
    }

    public void chooseActToCancel(ArrayList<String[]> enrolled, String activityID)
            throws EventNotFoundException {
        ArrayList<String> actIDs = extractActIDHelper(enrolled);
        if(actIDs.contains(activityID)){
            cancelEnrollmentUpdate(userName, activityID);
        }
        else{
            throw new EventNotFoundException("You aren't enrolled in this activity");
        }
    }

    private void cancelEnrollmentUpdate(ArrayList<String> userName, String activityID){
        UUID actID = UUID.fromString(activityID);
        eventManager.removeAttendee(actID, userManager.currentUsername());
        messageRoomManager.removeUser(userName, eventManager.getConferenceChat(actID));
        String[] actInfo = eventManager.searchEventByUUID(activityID);
        LocalDateTime[] time = getTimeHelper(actInfo);
        userManager.deleteEvent(time);
    }
}
