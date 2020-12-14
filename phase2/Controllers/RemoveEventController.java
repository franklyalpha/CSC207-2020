package Controllers;

import java.time.LocalDateTime;
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


