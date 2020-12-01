package Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class CancelEventController extends ActivityController{


    public CancelEventController(UserController userController) {
        super(userController);
    }

    public ArrayList<String[]> findEmptyEvents(){
        ArrayList<String[]> events = activityManager.viewUpcommingActivites();
        ArrayList<String[]> emptyEvents = new ArrayList<>();
        for (String[] actinfo: events){
            if(activityManager.numAttendee(UUID.fromString(actinfo[0])) == 0){
                emptyEvents.add(new String[]{actinfo[0], actinfo[1]});
            }
        }
        return emptyEvents;
    }

    public void cancelAndUpdate(String eventID){
        // need to update the speaker as an array list of speaker;
        UUID actID = UUID.fromString(eventID);
        String[] actInfo = activityManager.searchActivityByUUID(eventID);
        String speaker = actInfo[5];
        LocalDateTime[] period = getTimeHelper(actInfo);
        activityManager.deleteEvent(actID);
        userManager.deleteActivity(speaker, period);
    }


}
