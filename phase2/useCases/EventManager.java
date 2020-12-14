package useCases;

import entities.*;
import globallyAccessible.EventType;

import java.time.*;
import java.util.*;


/**
 * Represents a <code>EventManager</code> that is responsible for any change with <code>Event</code> entities.
 * Also contains an instance of <code>Event</code> that contains all time, topic, attendees at an activity.
 *
 * Include:
 * Own constructor;
 * addNewEvent: will create and store a new activity.
 * searchEventByUUID: will return a description of certain activity, searched by by UUID.
 * addSpeaker: will add/replace speakers of a event.
 * getEventChat: will return the UUID of unique chat associated with this event.
 * viewUpcomingEvents: will provide info for all events not yet begun.
 * addAttendee: will add an attendee towards participant list of this event.
 * numAttendee: will return the number of participants currently have.
 * removeAttendee: will remove an attendee from participant list.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class EventManager extends AbstractSerializableManager implements java.io.Serializable{

    protected HashMap<EventType,ArrayList<Event>> upcomingEvents;

    protected HashMap<EventType,ArrayList<Event>> archivedEvents;

    /**
     * Constructor of <code>EventManager</code>, will create blank array lists for upcoming activities and
     * archived activities.
     */
    public EventManager(){
        this.upcomingEvents = new HashMap<>();
        upcomingEvents.put(EventType.TALK, new ArrayList<>());
        upcomingEvents.put(EventType.PANEL, new ArrayList<>());
        upcomingEvents.put(EventType.PARTY, new ArrayList<>());
        this.archivedEvents = new HashMap<>();
        archivedEvents.put(EventType.TALK, new ArrayList<>());
        archivedEvents.put(EventType.PANEL, new ArrayList<>());
        archivedEvents.put(EventType.PARTY, new ArrayList<>());
    }

    /**
     * Constructor used by the subclasses as we do not want to store any other type of Events
     * @param type The type that the subclass is handling
     * @param upcoming An ArrayList of upcoming Events that contains exclusively that type of event
     * @param archived An ArrayList of archived Events that contains exclusively that type of event
     */
    public EventManager(EventType type, ArrayList<Event> upcoming, ArrayList<Event> archived){
        this.upcomingEvents = new HashMap<>();
        upcomingEvents.put(type, upcoming);
        this.archivedEvents = new HashMap<>();
        archivedEvents.put(type, archived);
    }

    /**
     * Creating a event through factory design pattern
     * @param period LocalDateTime of start & end time in <code>Event</code>.
     * @param roomID UUID of assigned message room ID and event room ID <code>Event</code>.
     * @param topic the topic of the event in <code>Event</code>.
     * @param MaxNum the maximum number of attendees for this event
     * @param eventType the type of event we want to create
     * @return the UUID of the event we just created
     */
    public UUID createEvent(LocalDateTime[] period, UUID[] roomID, String topic,
                              Integer MaxNum, EventType eventType) {
        return new EventFactory(this).construct(period, roomID, topic, MaxNum, eventType);
    }

    /**
     * Add the recently created event to upcoming events
     * @param event the event we are adding to upcoming events
     * @param type the type of that event
     * @return the UUID of the event
     */
    public UUID addEvent(Event event, EventType type){
        if (!upcomingEvents.containsKey(type)){
            upcomingEvents.put(type, new ArrayList<>());
        }
        upcomingEvents.get(type).add(event);
        return event.getIdentity();
    }

    /**
     * returns all of the upcoming events
     * @return an ArrayList of all upcoming events
     */
    public ArrayList<Event> allUpcomingEvents(){
        ArrayList<Event> finalList = new ArrayList<>();
        for(ArrayList<Event> events: upcomingEvents.values()){
            finalList.addAll(events);
        }
        return finalList;
    }

    /**
     * Will return the description of activity in an array of strings.
     * @param ID the <code>String</code> form of UUID of given activity.
     * @return an array of <code>String</code>, with six elements; each one is:
     * UUID of this event (in string), topic, start time, end time, room's UUID,
     * and username of speaker.
     */
    public String[] searchEventByUUID(String ID){
        for(Event i: allUpcomingEvents()){
            if(ID.equals(i.getIdentity().toString())){
                return new String[]{i.getIdentity().toString(), i.getTopic(),
                        i.getStartTime().toString(), i.getEndTime().toString(),
                        i.getEventRoomNum().toString(), i.toString(), i.getEventType().toString(), i.speakerToString()};
            }
        }
        return null;
    }

    /**
     * a private helper to get the start and end time of this event.
     * @param act the event in question
     * @return the Array containing the start and end time of this event
     */
    private LocalDateTime[] timeProcessing(Event act){
        LocalDateTime[] time = new LocalDateTime[2];
        time[0] = act.getStartTime();
        time[1] = act.getEndTime();
        return time;
    }

    /**
     * Finds the event in all upcoming events with the same UUID
     * @param actID the ID of event we want to find
     * @return the event with the same actID
     */
    protected Event findEvent(UUID actID){
        for (Event act : allUpcomingEvents()){
            if (actID.equals(act.getIdentity())){
                return act;
            }
        }
        return null;
    }

    /**
     * Find the type of the event with the same ID
     * @param actID the ID of the event we want to find
     * @return the type of that event
     */
    public EventType findType(UUID actID){
        for (Event act : allUpcomingEvents()){
            if (actID.equals(act.getIdentity())){
                return act.getEventType();
            }
        }
        return null;
    }

    /**
     * Will return the UUID of event assigned chat with given event.
     * @param actID <code>UUID</code> of event requiring info of corresponding event chat.
     * @return <code>UUID</code> of corresponding chat.
     */
    public UUID getEventChat(UUID actID){
        Event targetAct = findEvent(actID);
        assert targetAct != null;
        return targetAct.getChatID();
    }

    /**
     * return info of all upcoming activities.
     * @return an <code>ArrayList</code> of array of <code>String</code>, where each array of
     * String has 8 elements, represents: UUID of this event, topic, start time, end time,
     * UUID of assigned room, the event's description, the type of event and name of speaker(s).
     */
    public ArrayList<String[]> viewUpcomingEvents(){
        ArrayList<String[]> result = new ArrayList<>();
        if (allUpcomingEvents() == null) {
            return result;
        }else{
            for (Event i : allUpcomingEvents()) {
                String[] temp = {i.getIdentity().toString(), i.getTopic(),
                        i.getStartTime().toString(), i.getEndTime().toString(),
                        i.getEventRoomNum().toString(), i.toString(), i.getEventType().toString(), i.speakerToString()};
                result.add(temp);
            }
        }
        return result;
    }

    /**
     * The method that is being overridden in the subclass to condense functionality in the controller layer
     * @param actID ID of event
     * @param speakerName Name of the speaker
     */
    public void addSpeaker(UUID actID, String speakerName){}

    /**
     * will add the given attendee's name into participant list.
     * @param eventID the UUID of event requiring adding an attendee.
     * @param attendee the username of attendee who is going to enroll into the given event.
     * @return true iff the attendee is added successfully. 'false' otherwise.
     */
    //method that add attendees
    public boolean addAttendee(UUID eventID,String attendee){
        Event a = findEvent(eventID);
        return a.addAttendeesToList(attendee);
    }

    /**
     * return the number of existing attendees participated in given activity.
     * @param eventID the UUID of event that the user wants to know about number of participants.
     * @return an <code>int</code> representing number of participants for this event.
     */
    //method that get the num of attendees in certain activity.
    public int numAttendee(UUID eventID){
        Event a = findEvent(eventID);
        return a.getAttendeeList().size();
    }

    /**
     * Will remove the given attendee from given event represented by UUID.
     * @param eventID: <code>UUID</code> of given event requiring removing attendee.
     * @param attendee: a <code>String</code> representing the username of attendee
     *                 needed to be deleted.
     * @return true iff the attendee is deleted successfully. (false otherwise)
     */
    //method that remove the attendee from certain activity.
    public boolean removeAttendee(UUID eventID,String attendee) {
        Event a = findEvent(eventID);
        return a.removeAttendee(attendee);

    }

    /**
     * Delete the event with the eventID
     * @param eventID the ID of the target event
     */
    public void deleteEvent(UUID eventID){
        EventType activityType = findType(eventID);
        Event potentialCancel = findEvent(eventID);
        upcomingEvents.get(activityType).remove(potentialCancel);
    }

    /**
     * returns the attendee list for this event
     * @param eventID ID used to search the event
     * @return the attendee list
     */
    public ArrayList<String> getAttendeeList(UUID eventID){
        return findEvent(eventID).getAttendeeList();
    }

    /**
     * Used to modify the max participant limit on events
     * @param eventId ID of such event
     * @param newMaxNum the new max limit
     */
    public void changeEventMaxParticipant(UUID eventId, Integer newMaxNum){
        Objects.requireNonNull(findEvent(eventId)).setMaxNumAttendee(newMaxNum);
    }

    /**
     * Used to get the MaxNum of attendees for the event
     * @param eventId ID used to search the event
     * @return the MaxNum of the event
     */
    public int getEventMaxParticipant(UUID eventId){
        return findEvent(eventId).getMaxNumAttendee();
    }

}