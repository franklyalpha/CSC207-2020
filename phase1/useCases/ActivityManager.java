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
 * addAttendant: will add an attendant towards participant list of this conference.
 * numAttendant: will return the number of participants currently have.
 * removeAttendant: will remove an attendant from participant list.
 */
public class ActivityManager implements java.io.Serializable{

    /**
     * @param upcomingActivities: activities that are currently not begin.
     * @param archivedActivities: activities that have completed.
     */
    private final ArrayList<Activity> upcomingActivities;
    private final ArrayList<Activity> archivedActivities;
    // consider modifying as Hashmaps


    /**
     * Constructor of <code>ActivityManager</code>, will create blank array lists for upcoming activities and
     * archived activities.
     */
    public ActivityManager(){
        this.upcomingActivities = new ArrayList<Activity>();
        this.archivedActivities = new ArrayList<Activity>();
    }

    /**
     * will create and store a new activity with given parameters.
     * @param period an array storing <code>LocalDateTime</code> of start time and end time;
     * @param chatRoomID an array storing <code>UUID</code> of assigned chat and assigned room.
     * @param topic a <code>String</code> representing the topic of this activity.
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
     * Will return the description of activity in an array of strings.
     * @param ID the <code>String</code> form of UUID of given activity.
     * @return an array of <code>String</code>, with six elements; each one is:
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

    private LocalDateTime[] timeProcessing(Activity act){
        LocalDateTime[] time = new LocalDateTime[2];
        time[0] = act.getStartTime();;
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
     * will add the given attendant's name into participant list.
     * @param activity the UUID of activity requiring adding an attendant.
     * @param attendant the username of attendant who is going to enroll into the given activity.
     * @return true iff the attendant is added successfully. 'false' otherwise.
     */
    //method that add attendees
    public boolean addAttendant(UUID activity,String attendant){
        Activity a = findActivity(activity);
        return a.addAttendantsToList(attendant);
    }

    /**
     * return the number of existing attendants participated in given activity.
     * @param activity the UUID of activity that the user wants to know about number of participants.
     * @return an <code>int</code> representing number of participants for this activity.
     */
    //method that get the num of attendees in certain activity.
    public int numAttendant(UUID activity){
        Activity a = findActivity(activity);
        return a.getAttendantList().size();
    }

    /**
     * Will remove the given attendant from given activity represented by UUID.
     * @param activity: <code>UUID</code> of given activity requiring removing attendant.
     * @param attendant: a <code>String</code> representing the username of attendant
     *                 needed to be deleted.
     * @return true iff the attendant is deleted successfully. (false otherwise)
     */
    //method that remove the attendee from certain activity.
    public boolean removeAttendant(UUID activity,String attendant) {
        Activity a = findActivity(activity);
        return a.removeAttendant(attendant);

    }



    //speaker identity duration topic roomnum starttime
    //public ArrayList<entities.Activity> searchActivityByParameter(E parameter, char paraType, boolean ArchivedYN){}
}