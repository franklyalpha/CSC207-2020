package ActivityControllers;

import presenter.Presenter;
import useCases.ActivityManager;
import useCases.MessageRoomManager;
import useCases.RoomManager;
import useCases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserActivityController {

    protected RoomManager roomManager;
    protected ActivityManager activityManager;
    protected UserManager userManager;
    protected MessageRoomManager messageRoomManager;

    public UserActivityController(Object[] managers){
        roomManager = (RoomManager) managers[3];
        activityManager = (ActivityManager) managers[1];
        userManager = (UserManager) managers[2];
        messageRoomManager = (MessageRoomManager) managers[0];
    }


    public ArrayList<String[]> viewEnrolledSchedule() {
        HashMap<LocalDateTime[], UUID> schedules = userManager.schedules();
        ArrayList<String[]> allSchedule = new ArrayList<>();
        for (LocalDateTime[] time : schedules.keySet()){
            String[] partialInfo = activityManager.searchActivityByUUID(schedules.get(time).toString());
            allSchedule.add(partialInfo);
        }
        Presenter.printSchedule(allSchedule);
        return allSchedule;
    }

    /**
     * Helper function for getting the UUIDs of available activities.
     * @param available ArrayList of Strings representing all available activities.
     * @return Returns an ArrayList of Strings representing the UUIDs of all available activities.
     */
    protected ArrayList<String> extractActIDHelper (ArrayList<String[]> available){
        ArrayList<String> actIDs = new ArrayList<>();
        for (String[] schedule: available){
            actIDs.add(schedule[0]);
        }
        return actIDs;
    }

    /**
     * Helper function for formatting user-inputted times.
     * @param scheduleInfo String representing user-inputted time information, to be formatted.
     * @return Returns the formatted time information in LocalDateTime format.
     */
    protected LocalDateTime[] getTimeHelper(String[] scheduleInfo){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime[] time = {LocalDateTime.parse(scheduleInfo[2], df),
                LocalDateTime.parse(scheduleInfo[3], df)};
        return time;
    }

    /**
     * Print out the schedule of all activities this attendee can sign in.
     * Information include: topic of activity, start time and end time, ID of assigned room,
     * ID of this activity and name of speaker.
     * Specific format will be determined in Presenter.
     */
    public void viewAvailableSchedules(){
        ArrayList<String[]> result = this.availableSchedules();
        Presenter.printSchedule(result);
    }

    ArrayList<String[]> availableSchedules(){
        ArrayList<String[]> schedules = activityManager.viewUpcommingActivites();
        ArrayList<String> temp = new ArrayList<>();

        //activity that is full and user is not free.
        for(String[] info: schedules){
            if(checkIsConflict(info)){
                temp.add(activityManager.searchActivityByUUID(info[0])[0]);
            }
        }
        schedules.removeIf(info -> temp.contains(info[0]));
        return schedules;
    }

    private boolean checkIsConflict(String[] info){
        if (!roomManager.CheckRoomFullness(activityManager.numAttendee(UUID.fromString(info[0])),
                UUID.fromString(info[4]))){
            return true;
        }
        LocalDateTime[] time = getTimeHelper(info);
        return !userManager.isFree(time);
    }
}
