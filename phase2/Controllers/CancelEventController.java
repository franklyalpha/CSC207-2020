package Controllers;

import globallyAccessible.EventType;
import useCases.OrganizerManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


/**
 * This is a controller responsible for deleting an existing event of conference. Extends from <>AbstractController</>.
 */
public class CancelEventController extends EventController {
    /**
     * An instance of <>OrganizerManager</>.
     */
    protected OrganizerManager organizerManager;

    /**
     * The constructor of this controller.
     * @param userController An instance of UserController.
     */
    public CancelEventController(UserController userController) {
        super(userController);
        organizerManager = new OrganizerManager(userManager);
    }

    /**
     * Return a list containing information of events without attendee participation.
     *
     * @return an instance of <>ArrayList<String[]></> which contains information of events having no attendee.
     * Each <>String[]</> in <>Arraylist</> contains UUID and topic of a given event.
     */
    public ArrayList<String[]> findEmptyEvents(){
        ArrayList<String[]> events = eventManager.viewUpcomingEvents();
        ArrayList<String[]> emptyEvents = new ArrayList<>();
        for (String[] actInfo : events){
            if(eventManager.numAttendee(UUID.fromString(actInfo[0])) == 0){
                emptyEvents.add(new String[]{actInfo[0], actInfo[1]});
            }
        }
        return emptyEvents;
    }


    /**
     * Deletes an event given its ID, and update <>User</>'s schedule and <>MessageRoom</>, <>room</> associated with this event.
     * @param eventID The ID of given event wants to be cancelled.
     */
    public void cancelAndUpdate(String eventID){
        // need to update the speaker as an array list of speaker;
        String[] actInfo = eventManager.searchEventByUUID(eventID);
        UUID chatID = eventManager.getEventChat(UUID.fromString(eventID));
        messageRoomManager.deleteGroupChat(chatID);
        LocalDateTime[] period = getTimeHelper(actInfo);
        processCancelSpeaker(eventID);
        eventManager.deleteEvent(UUID.fromString(eventID));
        roomManager.CancelRoomEvent(period, UUID.fromString(eventID), UUID.fromString(actInfo[4]));
    }

    /**
     * Updates all speaker's schedule so they will be free at original event's period,
     * after the event is cancelled.
     * @param eventID the ID of event wish to cancel.
     */
    protected void processCancelSpeaker(String eventID){
        String[] actInfo = eventManager.searchEventByUUID(eventID);
        if(actInfo == null) return;
        UUID actID = UUID.fromString(eventID);
        EventType eventType = eventManager.findType(actID);
        LocalDateTime[] period = getTimeHelper(actInfo);
        cancelUserSchedule(actID, eventType, period);
    }

    private void cancelUserSchedule(UUID actID, EventType eventType, LocalDateTime[] period) {
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
