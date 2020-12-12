package entities;

import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Party is an event without any speakers.
 * The added functionality is a more specific toString method.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class Party extends Event {

    /**
     * Creates <code>Party</code> with specified start time, end time, conference chat id, conference room number,
     * max attendees, and topic.
     * @param period LocalDateTime of start & end time in <code>Activity</code>.
     * @param chatRoomID UUID of assigned chatroom ID and room ID <code>Activity</code>.
     * @param topic the topic of the activity in <code>Activity</code>.
     * @param MaxNum the maximum number of attendees for this event
     */
    public Party(LocalDateTime[] period, UUID[] chatRoomID,
                 String topic, Integer MaxNum){
        super(period, chatRoomID, topic, MaxNum);
        type = EventType.PARTY;
    }

    /**
     * getter for the event type
     * @return the type of this event
     */
    public EventType getEventType(){return type;}

    /**
     * toString method based on the super toString method with add parameter: type.
     * @return the information contained in this Party entity
     */
    @Override
    public String toString() {
        return "Type: Party\n" +
                super.toString();
    }
}
