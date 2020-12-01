package Controllers;

import globallyAccessible.ActivityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class QuitActivityController extends ActivityController {
    private ArrayList<String> userName;

    public QuitActivityController(UserController userController) {
        super(userController);
        userName = new ArrayList<>();
        userName.add(userManager.currentUsername());
    }

    public void chooseActToCancel(ArrayList<String[]> enrolled, String activityID)
            throws ActivityNotFoundException {
        ArrayList<String> actIDs = extractActIDHelper(enrolled);
        if(actIDs.contains(activityID)){
            cancelEnrollmentUpdate(userName, activityID);
        }
        else{
            throw new ActivityNotFoundException("You aren't enrolled in this activity");
        }
    }

    private void cancelEnrollmentUpdate(ArrayList<String> userName, String activityID){
        UUID actID = UUID.fromString(activityID);
        activityManager.removeAttendee(actID, userManager.currentUsername());
        messageRoomManager.removeUser(userName,activityManager.getConferenceChat(actID));
        String[] actInfo = activityManager.searchActivityByUUID(activityID);
        LocalDateTime[] time = getTimeHelper(actInfo);
        userManager.deleteActivity(time);
    }
}
