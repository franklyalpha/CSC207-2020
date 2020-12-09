package useCases;

import entities.*;
import globallyAccessible.EventType;

import java.time.*;
import java.util.*;


/**
 * Represents a <code>ActivityManager</code> that is responsible for any change with <code>Activity</code> entities.
 * Also contains an instance of <code>Activity</code> that contains all time, topic, attendents at an activity.
 *
 * Include:
 * Own constructor;
 * addNewActivity: will create and store a new activity.
 * searchActivityByUUID: will return a description of certain activity, searched by by UUID.
 * addSpeaker: will add/replace speakers of a conference.
 * getConferenceChat: will return the UUID of unique chat associated with this confernece.
 * viewUpcommingActivites: will provide info for all activities not yet begun.
 * addAttendee: will add an attendee towards participant list of this conference.
 * numAttendee: will return the number of participants currently have.
 * removeAttendee: will remove an attendee from participant list.
 */
public class EventManager extends AbstractSerializableManager implements java.io.Serializable{

    protected HashMap<EventType,ArrayList<Event>> upcomingEvents;

    protected HashMap<EventType,ArrayList<Event>> archivedEvents;

    /**
     * Constructor of <code>ActivityManager</code>, will create blank array lists for upcoming activities and
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

    public EventManager(EventType type, ArrayList<Event> upcoming, ArrayList<Event> archived){
        this.upcomingEvents = new HashMap<>();
        upcomingEvents.put(type, upcoming);
        this.archivedEvents = new HashMap<>();
        archivedEvents.put(type, archived);
    }

    public UUID createEvent(LocalDateTime[] period, UUID[] chatRoomID, String topic,
                              Integer MaxNum, EventType eventType) {
        return new EventFactory(this).construct(period, chatRoomID, topic, MaxNum, eventType);
    }

    public UUID addEvent(Event event, EventType type){
        if (!upcomingEvents.containsKey(type)){
            upcomingEvents.put(type, new ArrayList<>());
        }
        upcomingEvents.get(type).add(event);
        return event.getIdentity();
    }

    public ArrayList<Event> allUpcomingEvents(){
        ArrayList<Event> finalList = new ArrayList<>();
        for(ArrayList<Event> i: upcomingEvents.values()){
            finalList.addAll(i);
        }
        return finalList;
    }
/*
    public boolean markActivityDone(Activity act){
        if(upcomingActivities.contains(act)){
            archivedActivities.add(act);
            upcomingActivities.remove(act);
            return true;
        }else{
            return false;
        }
    }
*/
    //TODO full and condensed search system
    /*public ArrayList<Activity> searchActivityByTopic(String topic){
        ArrayList<Activity> results = new ArrayList<Activity>();
        for(Activity i: this.upcomingActivities){
            if(i.getTopic().equals(topic)){
                results.add(i);
            }
        }
        return results;
    }

    public ArrayList<Activity> searchActivityBySpeaker(String speaker){
        ArrayList<Activity> results = new ArrayList<Activity>();
        for(Activity i: this.upcomingActivities){
            if(i.getSpeakersList().contains(speaker)){
                results.add(i);
            }
        }
        return results;
    }*/

    /**
     * Will return the description of activity in an array of strings.
     * @param ID the <code>String</code> form of UUID of given activity.
     * @return an array of <code>String</code>, with six elements; each one is:
     * UUID of this activity (in string), topic, start time, end time, room's UUID,
     * and username of speaker.
     */
    public String[] searchEventByUUID(String ID){
        for(Event i: allUpcomingEvents()){
            if(ID.equals(i.getIdentity().toString())){
                return new String[]{i.getIdentity().toString(), i.getTopic(),
                        i.getStartTime().toString(), i.getEndTime().toString(),
                        i.getConferenceRoomNum().toString(), i.toString(), i.getEventType().toString(), i.speakerToString()};
            }
        }
        return null;
    }

    private LocalDateTime[] timeProcessing(Event act){
        LocalDateTime[] time = new LocalDateTime[2];
        time[0] = act.getStartTime();
        time[1] = act.getEndTime();
        return time;
    }

    protected Event findEvent(UUID actID){
        for (Event act : allUpcomingEvents()){
            if (actID.equals(act.getIdentity())){
                return act;
            }
        }
        return null;
    }

    public EventType findType(UUID actID){
        for (Event act : allUpcomingEvents()){
            if (actID.equals(act.getIdentity())){
                return act.getEventType();
            }
        }
        return null;
    }

    /**
     * Will return the UUID of conference assigned chat with given conference.
     * @param actID <code>UUID</code> of activity requiring info of corresponding activity chat.
     * @return <code>UUID</code> of corresponding chat.
     */
    public UUID getConferenceChat(UUID actID){
        Event targetAct = findEvent(actID);
        assert targetAct != null;
        return targetAct.getChatID();
    }

    /**
     * return info of all upcoming activities.
     * @return an <code>ArrayList</code> of array of <code>String</code>, where each array of
     * String has six elements, represents: UUID of this activity, topic, start time, end time,
     * UUID of assigned room and name of speaker.
     */
    public ArrayList<String[]> viewUpcomingActivities(){
        ArrayList<String[]> result = new ArrayList<String[]>();
        if (allUpcomingEvents() == null) {
            return result;
        }else{
            for (Event i : allUpcomingEvents()) {
                String[] temp = {i.getIdentity().toString(), i.getTopic(),
                        i.getStartTime().toString(), i.getEndTime().toString(),
                        i.getConferenceRoomNum().toString(), i.toString(), i.getEventType().toString()};
                result.add(temp);
            }
        }
        return result;
    }

    public void addSpeaker(UUID actID, String speakerName){

    }

    /**
     * will add the given attendee's name into participant list.
     * @param activity the UUID of activity requiring adding an attendee.
     * @param attendee the username of attendee who is going to enroll into the given activity.
     * @return true iff the attendee is added successfully. 'false' otherwise.
     */
    //method that add attendees
    public boolean addAttendee(UUID activity,String attendee){
        Event a = findEvent(activity);
        return a.addAttendeesToList(attendee);
    }

    /**
     * return the number of existing attendees participated in given activity.
     * @param activity the UUID of activity that the user wants to know about number of participants.
     * @return an <code>int</code> representing number of participants for this activity.
     */
    //method that get the num of attendees in certain activity.
    public int numAttendee(UUID activity){
        Event a = findEvent(activity);
        return a.getAttendeeList().size();
    }

    /**
     * Will remove the given attendee from given activity represented by UUID.
     * @param activity: <code>UUID</code> of given activity requiring removing attendee.
     * @param attendee: a <code>String</code> representing the username of attendee
     *                 needed to be deleted.
     * @return true iff the attendee is deleted successfully. (false otherwise)
     */
    //method that remove the attendee from certain activity.
    public boolean removeAttendee(UUID activity,String attendee) {
        Event a = findEvent(activity);
        return a.removeAttendee(attendee);

    }

    public void deleteEvent(UUID activityID){
        upcomingEvents.remove(findType(activityID), findEvent(activityID));
    }

    public void changeEventMaxParticipant(UUID activityId, Integer newMaxNum){
        Objects.requireNonNull(findEvent(activityId)).setMaxNumAttendee(newMaxNum);
    }

    public int getEventMaxParticipant(UUID activityId){
        return findEvent(activityId).getMaxNumAttendee();
    }

    //speaker identity duration topic roomnum starttime
    //public ArrayList<entities.Activity> searchActivityByParameter(E parameter, char paraType, boolean ArchivedYN){}
}