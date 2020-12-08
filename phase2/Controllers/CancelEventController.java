package Controllers;

import globallyAccessible.EventType;
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
        ArrayList<String[]> events = eventManager.viewUpcomingActivities();
        ArrayList<String[]> emptyEvents = new ArrayList<>();
        for (String[] actInfo : events){
            if(eventManager.numAttendee(UUID.fromString(actInfo[0])) == 0){
                emptyEvents.add(new String[]{actInfo[0], actInfo[1]});
            }
        }
        return emptyEvents;
    }

    public void cancelAndUpdate(String eventID){
        // need to update the speaker as an array list of speaker;
        eventManager.deleteEvent(UUID.fromString(eventID));
        processCancelSpeaker(eventID);

    }

    private void processCancelSpeaker(String eventID){
        String[] actInfo = eventManager.searchEventByUUID(eventID);
        UUID actID = UUID.fromString(eventID);
        EventType eventType = eventManager.findType(actID);
        LocalDateTime[] period = getTimeHelper(actInfo);
        if(eventType == EventType.TALK){
            String speaker = talkManager.getSpeaker(actID);
            organizerManager.deleteEvent(speaker, period);
        }else if(eventType == EventType.PANEL){
            ArrayList<String> speakers = panelManager.getSpeakers(actID);
            for(String i: speakers){
                organizerManager.deleteEvent(i, period);
            }
        }
    }


}
