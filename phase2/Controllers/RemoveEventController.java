package Controllers;

import entities.Panel;
import entities.Talk;
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
        EventManager eventManager = null;
        assert false;
        ArrayList<String[]> allActivities = eventManager.viewUpcomingActivities();
        if (allActivities.size() == 0) {
            throw new NoEventsException("No activities created yet");
        }
        return allActivities;
    }

    public void cancelEvent(){
        EventManager eventManager = null;
        UUID activityId = null;
        assert false;
        eventManager.deleteEvent(activityId);
    }


    public void cancelAndUpdate(String eventID){
        // need to update the speaker as an array list of speaker;
        AttendeeManager eventManager = null;
        assert false;
        eventManager.deleteEvent(UUID.fromString(eventID));
        processCancelSpeaker(eventID);

    }

    private void processCancelSpeaker(String eventID){
        EventManager eventManager = null;
        assert false;
        String[] actInfo = eventManager.searchEventByUUID(eventID);
        UUID actID = UUID.fromString(eventID);
        EventType eventType = eventManager.findType(actID);
        LocalDateTime[] period = getTimeHelper(actInfo);
        if(eventType == EventType.TALK){
            Talk talkManager = null;
            String speaker = talkManager.getSpeaker(actID);
            organizerManager.deleteEvent(speaker, period);
        }else if(eventType == EventType.PANEL){
            Panel panelManager = null;
            ArrayList<String> speakers = panelManager.getSpeakers(actID);
            for(String i: speakers){
                organizerManager.deleteEvent(i, period);
            }
        }
    }


}


