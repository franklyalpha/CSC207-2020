package Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


/**
 * Responsible for removing an event and updating all relevant information.
 */
public class RemoveEventController extends CancelEventController {


    /**
     * the constructor of this class;
     * @param userController: an instance of <>UserController</>.
     */
    public RemoveEventController(UserController userController) {
        super(userController);
    }

    /**
     * responsible for deleting an existing <>Event</> and updating all relevant info.
     * @param eventID the <>UUID</> of <>Event</> about to be cancelled.
     */
    public void cancelAndUpdate(UUID eventID){
        // need to update the speaker as an array list of speaker;
        String[] actInfo = eventManager.searchEventByUUID(eventID.toString());
        if(actInfo == null) return;
        processCancelSpeaker(eventID.toString());
        UUID chatID = eventManager.getEventChat(eventID);
        messageRoomManager.deleteGroupChat(chatID);
        processCancelAttendee(eventID);
        updateRoomEventManager(eventID, actInfo);
    }

    private void updateRoomEventManager(UUID eventID, String[] actInfo) {
        LocalDateTime[] period = getTimeHelper(actInfo);
        processCancelSpeaker(eventID.toString());
        eventManager.deleteEvent(eventID);
        eventRoomManager.CancelRoomEvent(period, eventID, UUID.fromString(actInfo[4]));
    }

    private void processCancelAttendee(UUID eventID){
        ArrayList<String> attendeeList = eventManager.getAttendeeList(eventID);
        if(attendeeList.size() == 0) return;
        for(String attendee: attendeeList){
            String[] actInfo = eventManager.searchEventByUUID(eventID.toString());
            LocalDateTime[] period = getTimeHelper(actInfo);
            organizerManager.deleteEvent(attendee, period);
        }
    }


}


