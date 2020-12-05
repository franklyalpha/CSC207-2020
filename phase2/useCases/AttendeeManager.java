package useCases;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class AttendeeManager extends UserManager {
    public AttendeeManager(UserManager userManager){
        super(userManager);
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
    public void selfAddSchedule(LocalDateTime[] time, UUID actID){ ;
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
}
