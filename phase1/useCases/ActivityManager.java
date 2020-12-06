package useCases;

import entities.Activity;

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
public class ActivityManager implements java.io.Serializable{

    /**
     * ArrayList of all instances of <code>Activity</code> have not begun yet.
     */
    private final ArrayList<Activity> upcomingActivities;

    /**
     * ArrayList of all instances of <code>Activity</code> that have been finished.
     */
    private final ArrayList<Activity> archivedActivities;
    // consider modifying as Hashmaps


    /**
     * Creates <code>ActivityManager</code> to create two empty arraylist for the \
     * <code>upcomingActivity</code> and
     * <code>archivedActivity</code>list respectively.
     */
    public ActivityManager(){
        this.upcomingActivities = new ArrayList<Activity>();
        this.archivedActivities = new ArrayList<Activity>();
    }

    /**
     * will create and store a new activity with given parameters.
     * @param period is time period the activity, i.e. an array storing <code>LocalDateTime</code> of start time and end time;
     * @param chatRoomID is the id of the chatroom for the activity, \
     *                   i.e. an array storing <code>UUID</code> of assigned chat and assigned room.
     * @param topic is the topic of the activity, i.e. a <code>String</code> representing the topic of this activity.
     * @return UUID of newly created activity.
     */
    public UUID addNewActivity(LocalDateTime[] period, UUID[] chatRoomID, String topic){
        Activity newAct = new Activity(period, chatRoomID, topic);
        upcomingActivities.add(newAct);
        return newAct.getIdentity();
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
     * get list of available identity, string, topic, StartTime, EndTime, ConferenceRoomNum,
     * SpeakerList for the given ID if possible.
     * Will return the description of activity in an array of strings.
     * @param ID the identity we want to search information for, \
     *           i.e. the <code>String</code> form of UUID of given activity.
     * @return a list of available dentity, string, topic, StartTime, EndTime, ConferenceRoomNum,
     *           SpeakerList if possible and return null otherwise. \
     *      i.e. an array of <code>String</code>, with six elements; each one is:
     * UUID of this activity (in string), topic, start time, end time, room's UUID,
     * and username of speaker.
     */
    public String[] searchActivityByUUID(String ID){
        for(Activity i: this.upcomingActivities){
            if(ID.equals(i.getIdentity().toString())){
                return new String[]{i.getIdentity().toString(), i.getTopic(),
                        i.getStartTime().toString(), i.getEndTime().toString(),
                        i.getConferenceRoomNum().toString(), i.getSpeakersList()};
            }
        }
        return null;
    }


    /**
     * create list of <code>LocalDateTime</code> for the given <code>Activity</code> instance.
     * @return the list of time containing both the startTime and the EndTime of the given activity.
     */
    private LocalDateTime[] timeProcessing(Activity act){
        LocalDateTime[] time = new LocalDateTime[2];
        time[0] = act.getStartTime();
        time[1] = act.getEndTime();
        return time;
    }

    /**
     * Will add given speaker to given conference.
     * @param actID an <code>UUID</code> representing the activity requiring assigning a speaker;
     * @param speakerName a <code>String</code> representing the username of speaker needed to be assigned.
     */
    public void addSpeaker(UUID actID, String speakerName){
        Activity targetAct = findActivity(actID);
        assert targetAct != null;
        targetAct.addSpeakers(speakerName);
    }


    /**
     * check if the activity for a specific <code>actID</code> is in the
     * <code>upcomingActivities</code> list.
     * @param actID is the identity for the target parameter.
     * @return the activity if we can find in the list and return null if we cannot.
     */

    private Activity findActivity(UUID actID){
        for (Activity act : upcomingActivities){
            if (actID.equals(act.getIdentity())){
                return act;
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
        Activity targetAct = findActivity(actID);
        assert targetAct != null;
        return targetAct.getChatID();
    }

    /**
     * return info of all upcoming activities.
     * @return an <code>ArrayList</code> of array of <code>String</code>, where each array of
     * String has six elements, represents: UUID of this activity, topic, start time, end time,
     * UUID of assigned room and name of speaker.
     */
    // method that get all upcomming Activites.
    //TODO remove the typo in all instance of this method
    public ArrayList<String[]> viewUpcommingActivites(){
        ArrayList<String[]> result = new ArrayList<String[]>();
        for(Activity i: this.upcomingActivities){
            String[] temp = {i.getIdentity().toString(), i.getTopic(),
                    i.getStartTime().toString(), i.getEndTime().toString(),
                    i.getConferenceRoomNum().toString(), i.getSpeakersList()};
            result.add(temp);
        }
        return result;
    }

    /**
     * check if a specific attendent should be added to a specific activity and
     * will add the given attendee's name into participant list.
     * @param activity the UUID of target activity requiring adding an attendee.
     * @param attendee the username of target attendee who is going to enroll into the given activity.
     * @return true iff the attendee is added to the activity successfully. 'false' otherwise.
     */
    //method that add attendees
    public boolean addAttendee(UUID activity,String attendee){
        Activity a = findActivity(activity);
        return a.addAttendeesToList(attendee);
    }

    /**
     * get the num of attendees in certain activity
     * @param activity the UUID of target activity that the user wants to know about number of participants.
     * @return the number of existing attendants participated in the given activity, \
     * i.e. an <code>int</code> representing number of participants for this activity
     * @param activity the UUID of target activity that the user wants to know about number of participants.
     */
    //method that get the num of attendees in certain activity.
    public int numAttendee(UUID activity){
        Activity a = findActivity(activity);
        return a.getAttendeeList().size();
    }

    /**
     //method that remove the attendee from certain activity.
     /**
     * remove the attendee from certain activity
     * Will remove the given attendee from given activity represented by UUID.
     * @param activity: the target activity we want to remove attendant from, i.e. \
     *                <code>UUID</code> of given activity requiring removing attendee.
     * @param attendee: a <code>String</code> representing the username of attendee
     *                 needed to be deleted.
     * @return true iff the attendee is deleted successfully. (false otherwise)
     */
    //method that remove the attendee from certain activity.
    public boolean removeAttendee(UUID activity,String attendee) {
        Activity a = findActivity(activity);
        return a.removeAttendee(attendee);

    }



    //speaker identity duration topic roomnum starttime
    //public ArrayList<entities.Activity> searchActivityByParameter(E parameter, char paraType, boolean ArchivedYN){}
}