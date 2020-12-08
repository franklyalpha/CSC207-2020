package Controllers;

import globallyAccessible.EventType;
import globallyAccessible.NoEventsException;
import useCases.AttendeeManager;
import useCases.EventManager;
import useCases.OrganizerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


public class RemoveEventController extends EventController {

    private final OrganizerManager organizerManager;

    public RemoveEventController(UserController userController) {
        super(userController);
        organizerManager = new OrganizerManager(userManager);
    }

    public ArrayList<String[]> getAllActivities() throws NoEventsException {
        ArrayList<String[]> allActivities = eventManager.viewUpcomingActivities();
        if (allActivities.size() == 0) {
            throw new NoEventsException("No activities created yet");
        }
        return allActivities;
    }

    public void cancelAndUpdate(UUID eventID){
        // need to update the speaker as an array list of speaker;
        eventManager.deleteEvent(eventID);
        processCancelSpeaker(eventID.toString());
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
            ArrayList<String> speakers = panelManager.getSpeakersList(actID.toString());
            for(String i: speakers){
                organizerManager.deleteEvent(i, period);
            }
        }
    }


}


