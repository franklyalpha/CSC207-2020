package useCases;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import entities.Attendee;
import java.lang.ClassCastException;

/**
 * Represents a <code>AttendeeManager</code> that is responsible for what the current attendee is doing.
 * Also contains an instance of <code>Attendee</code> that is the current user.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */
public class AttendeeManager extends UserManager {
    private Attendee currUser;

    /**
     * Creates an instance of <code>AttendeeManager</code>
     * @param userManager the <code>UserManager</code> used to create this Manager
     * @throws ClassCastException when the clase cannot be casted
     */
    public AttendeeManager(UserManager userManager) throws ClassCastException {
        super(userManager);
            currUser = (Attendee) userOnAir;
    }

    /**
     * Creates a <code>deleteActivity</code> and delete the activity.
     * @param time is the time of the activity that needed to be delete.
     */
    public boolean deleteEvent(LocalDateTime[] time){
        Set<LocalDateTime[]> period = userOnAir.getActivities().keySet();
        for (LocalDateTime[] target : period){
            if (target[0].equals(time[0]) && target[1].equals(time[1])){
                userOnAir.getActivities().remove(target);
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a <code>selfAddSchedule</code> and add the time of the activity to the user's schedule.
     * @param time is the time that needed to be added.
     * @param actID is the ID of the user.
     */
    public void selfAddSchedule(LocalDateTime[] time, UUID actID){
        userOnAir.getActivities().put(time, actID);
    }

    /**
     * Creates a <code>isFree</code> and check whether the user is free during some time.
     * @param actinterv is the time that needed to be checked.
     * @return returns 1 if the user is free during the time and returns 0 otherwise.
     */
    public boolean isFree(LocalDateTime[] actinterv){
        HashMap<LocalDateTime[], UUID> userSchedule = userOnAir.getActivities();
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
     * Returns a list of IDs of requests of the <code>Attendee</code>>.
     * @return ArrayList representing the UUID of the attendee's submitted requests.
     */
    public ArrayList<UUID> getUserRequests(){
        if (currUser.getRequests() != null) {
            return currUser.getRequests();
        }
        return new ArrayList<>();
    }

    /**
     * Changes the list of <code>UUID</code>s to the new input list.
     * @param newList New list with which the existing list of <code>UUID</code>s will be replaced with.
     */
    public void setUserRequests(ArrayList<UUID> newList){
        currUser.setRequests(newList);
    }
}
