package useCases;


import entities.User;
import globallyAccessible.UserType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class OrganizerManager extends UserManager {
    public OrganizerManager(UserManager userManager){
        super(userManager);
    }

    /**
     * Creates a <code>isFree</code> and check if the speaker is free during the time period.
     * @param speaker is the speaker that needed to be checked.
     * @param actinterv is the time that needed to be checked.
     * @return returns 1 if the speaker is free during the time and returns 0 otherwise.
     */
    private boolean isFree(User speaker, LocalDateTime[] actinterv){
        HashMap<LocalDateTime[], UUID> userSchedule = speaker.getActivities();
        for(LocalDateTime[] interv: userSchedule.keySet()){
            LocalDateTime start = interv[0];
            LocalDateTime end = interv[1];
            if (start.isBefore(actinterv[0]) && end.isAfter(actinterv[1])){
                return false;
            }
            if (start.isAfter(actinterv[0]) && start.isBefore(actinterv[1])){
                return false;
            }
            if (end.isAfter(actinterv[0]) && end.isBefore(actinterv[1])){
                return false;
            }
            if (start.isEqual(actinterv[0]) && end.isEqual(actinterv[1])){
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a <code>availableSpeakers</code> and get the speakers that are available during the time period.
     * @param targetTime is the time period that we want to check.
     * @return returns the ArrayList that contains all the speakers that are available.
     */
    public ArrayList<String> availableSpeakers(LocalDateTime[] targetTime){
        ArrayList<String> freeSpeaker = new ArrayList<String>();
        for (User users: allUsers.get(UserType.SPEAKER)){
            if (isFree(users, targetTime)){
                freeSpeaker.add(users.getUsername());
            }
        }
        return freeSpeaker;
    }

    /**
     * Creates a <code>allAttendee</code> and get all the attendees.
     * @return the ArrayList that contains all the attendees.
     */
    public ArrayList<String> allAttendee(){
        ArrayList<String> attendees = new ArrayList<String>();
        for (User users : allUsers.get(UserType.ATTENDEE)){
            attendees.add(users.getUsername());
        }
        return attendees;
    }

    /**
     * Creates a <code>deleteActivity</code> and delete the activity for the user.
     * @param username is the name of user that we want to operate.
     * @param time is the time of the activity that needed to be delete.
     */
    public boolean deleteEvent(String username, LocalDateTime[] time){
        User target = findUser(username);
        assert target != null;
        Set<LocalDateTime[]> period = target.getActivities().keySet();
        for (LocalDateTime[] enrolled : period){
            if (enrolled[0].equals(time[0]) && enrolled[1].equals(time[1])){
                target.getActivities().remove(enrolled);
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a <code>otherAddSchedule</code> and add the time of the activity to another user's schedule.
     * @param username is the name of the other user that needed to add.
     * @param time is the time that needed to be added.
     * @param actID is the ID of the user.
     */
    public void otherAddSchedule(String username, LocalDateTime[] time, UUID actID){
        User targetUser = findUser(username);
        assert targetUser != null;
        targetUser.getActivities().put(time, actID);
    }
}