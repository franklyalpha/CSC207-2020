package entities;

import globallyAccessible.EventType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Panel is a type of event that has multiple speakers.
 * These speakers are stored in an arraylist.
 * Added are the getter and setters for speakers as well as some string processing methods.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class Panel extends Event {
    /**
     * The ArrayList that stores the reference to speakers.
     */
    private ArrayList<String> speakers;

    /**
     * Creates <code>Panel</code> with specified start time, end time, event chat id, event room number,
     * max attendees, and topic.
     * @param period LocalDateTime of start & end time in <code>Event</code>.
     * @param roomID UUID of assigned message room ID in <code>Event</code>.
     * @param topic the topic of the event in <code>Event</code>.
     * @param MaxNum the maximum number of attendees for this event.
     */
    public Panel(LocalDateTime[] period, UUID[] roomID,
                 String topic, Integer MaxNum){
        super(period, roomID, topic, MaxNum);
        speakers = new ArrayList<>();
        type = EventType.PANEL;
    }

    /**
     * sets the speakers ArrayList
     * @param speakers the speaker names ArrayList that is being set in this Panel
     */
    public void setSpeakers(ArrayList<String> speakers){this.speakers = speakers;}

    /**
     * returns the ArrayList of speaker names
     * @return the speaker ArrayList contained in this Panel
     */
    public ArrayList<String> getSpeakers(){return speakers;}

    /**
     * getter for the event type
     * @return the type of this event
     */
    public EventType getEventType(){return type;}

    /**
     * Returning the information stored in this Panel in a String format
     * @return the toString method of the super class plus the event type and speaker names
     */
    @Override
    public String toString() {
        return "Type: [Panel]\n" +
                super.toString();
    }

    /**
     * To format the speakers' names into a single String
     * @return the String containing all of the speaker names in a readable format
     */
    @Override
    public String speakerToString(){
        StringBuilder finalList = new StringBuilder();
        for(String i: speakers){
            finalList.append(i).append(", ");
        }
        finalList.delete(finalList.length()-2, finalList.length());
        return finalList.toString();
    }
}
