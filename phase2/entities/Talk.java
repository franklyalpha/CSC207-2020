package entities;

import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A type of event that contains only one speaker.
 * Added functionality are getter and setter for speaker and also more specific toString method.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class Talk extends Event {

    /**
     * Stores the reference to the speaker
     */
    private String speaker;

    /**
     * Creates <code>Talk</code> with specified start time, end time, event chat id, event room number,
     * max attendees, and topic.
     * @param period LocalDateTime of start & end time in <code>Event</code>.
     * @param roomID UUID of assigned message room ID and event room ID <code>Event</code>.
     * @param topic the topic of the activity in <code>Event</code>.
     * @param MaxNum the maximum number of attendees for this event
     */
    public Talk(LocalDateTime[] period, UUID[] roomID,
                 String topic, Integer MaxNum){
        super(period, roomID, topic, MaxNum);
        type = EventType.TALK;
    }

    /**
     * Sets the speaker for this Talk event.
     * @param speaker the target speaker we want to have for this talk
     */
    public void setSpeaker(String speaker){
        this.speaker = speaker;
    }

    /**
     * getter for the speaker String
     * @return the speaker contained in this Talk
     */
    public String getSpeaker(){return this.speaker;}

    /**
     * getter for the type of this event
     * @return the type of this event
     */
    public EventType getEventType(){return type;}

    /**
     * this toString have additional information
     * @return the super toString with speaker and type appended.
     */
    @Override
    public String toString() {
        return "Type: Talk\n" +
                "Speakers: " + speaker +
                super.toString();
    }

    /**
     * helper method to return the speaker String
     * @return the speaker string
     */
    @Override
    public String speakerToString(){
        return speaker;
    }
}
