package useCases;

import entities.Activity;

import java.time.*;
import java.util.*;

public class ActivityManager implements java.io.Serializable{

    /**
     * modifications:
     * modify the way of storing activity lists as Hashmap<Hashtable<LocalDateTime[], String>,entities.Activity>;
     * Hashtable is similar to 'tuple' in python;  LocalDateTime[] is scheduled period; String is Speaker's name
     *
     * missing functioning:
     * 1: being able to check whether there's a scheduled activity during assigned time period;
     * 2: being able to search activities by available time period;
     * 3: being able to modify a scheduled period by time and speaker. (phase 2)
     * 4: being able to get all upcommingActivities.
     */

    private final ArrayList<Activity> upcomingActivities;
    private final ArrayList<Activity> archivedActivities;
    // consider modifying as Hashmaps

    public ActivityManager(){
        this.upcomingActivities = new ArrayList<Activity>();
        this.archivedActivities = new ArrayList<Activity>();
    }

    public UUID addNewActivity(LocalDateTime startTime, LocalDateTime endTime, UUID conferenceChat,
                               UUID conferenceRoomNum, String topic){
        Activity newAct = new Activity(startTime, endTime, conferenceChat, conferenceRoomNum, topic);
        upcomingActivities.add(newAct);
        return newAct.getIdentity();
    }

    public boolean markActivityDone(Activity act){
        if(upcomingActivities.contains(act)){
            archivedActivities.add(act);
            upcomingActivities.remove(act);
            return true;
        }else{
            return false;
        }
    }

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

    public UUID getConferenceChat(UUID actID){
        Activity targetAct = findActivity(actID);
        assert targetAct != null;
        return targetAct.getChatID();
    }

    // method that get all upcomming Activites.
    //TODO remove the typo in all instance of this method
    public ArrayList<String[]> viewUpcommingActivites(){
        ArrayList<String[]> result = new ArrayList<String[]>();
        for(Activity i: this.upcomingActivities){
            String[] temp = {i.getIdentity().toString(), i.getTopic(),
                    i.getStartTime().toString(), i.getEndTime().toString(),
                    i.getConferenceRoomNum().toString()};
            result.add(temp);
        }
        return result;
    }
    //method that add attendees
    public boolean addAttendant(UUID activity,String attendant){
        Activity a = findActivity(activity);
        return a.AddAttendants(attendant);
    }
    //method that get the num of attendees in certain activity.
    public int numAttendant(UUID activity){
        Activity a = findActivity(activity);
        return a.getAttendantsList().size();
    }
    //method that remove the attendee from certain activity.
    public boolean removeAttendant(UUID activity,String attendant) {
        Activity a = findActivity(activity);
        return a.removeAttendant(attendant);

    }



    //speaker identity duration topic roomnum starttime
    //public ArrayList<entities.Activity> searchActivityByParameter(E parameter, char paraType, boolean ArchivedYN){}
}