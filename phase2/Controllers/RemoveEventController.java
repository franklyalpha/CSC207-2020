package Controllers;

import globallyAccessible.EventType;
import globallyAccessible.NoEventsException;
import useCases.AttendeeManager;
import useCases.EventManager;
import useCases.OrganizerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


public class RemoveEventController extends CancelEventController {


    public RemoveEventController(UserController userController) {
        super(userController);
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
        String[] actInfo = eventManager.searchEventByUUID(eventID.toString());
        processCancelSpeaker(eventID.toString());
        UUID chatID = eventManager.getConferenceChat(eventID);
        messageRoomManager.deleteGroupChat(chatID);
        updateRoomEventManager(eventID, actInfo);
        processCancelAttendee(eventID);
    }

    private void updateRoomEventManager(UUID eventID, String[] actInfo) {
        LocalDateTime[] period = getTimeHelper(actInfo);
        processCancelSpeaker(eventID.toString());
        eventManager.deleteEvent(eventID);
        roomManager.CancelRoomEvent(period, eventID, UUID.fromString(actInfo[4]));
    }

    private void processCancelAttendee(UUID eventID){
        for(String attendee: eventManager.getAttendeeList(eventID)){
            String[] actInfo = eventManager.searchEventByUUID(eventID.toString());
            LocalDateTime[] period = getTimeHelper(actInfo);
            organizerManager.deleteEvent(attendee, period);
        }
    }


}


