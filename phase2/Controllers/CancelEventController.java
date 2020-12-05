package Controllers;

import useCases.OrganizerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class CancelEventController extends EventController {
    private OrganizerManager organizerManager;

    public CancelEventController(UserController userController) {
        super(userController);
        organizerManager = new OrganizerManager(userManager);
    }

    public ArrayList<String[]> findEmptyEvents(){
        ArrayList<String[]> events = eventManager.viewUpcommingActivites();
        ArrayList<String[]> emptyEvents = new ArrayList<>();
        for (String[] actinfo: events){
            if(eventManager.numAttendee(UUID.fromString(actinfo[0])) == 0){
                emptyEvents.add(new String[]{actinfo[0], actinfo[1]});
            }
        }
        return emptyEvents;
    }

    public void cancelAndUpdate(String eventID){
        // need to update the speaker as an array list of speaker;
        UUID actID = UUID.fromString(eventID);
        String[] actInfo = eventManager.searchEventByUUID(eventID);
        String speaker = actInfo[5];
        LocalDateTime[] period = getTimeHelper(actInfo);
        eventManager.deleteEvent(actID);
        organizerManager.deleteEvent(speaker, period);
    }


}
