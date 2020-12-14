package functionalityPresenters;

import Controllers.UserController;
import useCases.AttendeeManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * AvailableSchedulePresenter
 * @author Group 0168
 * @author CSC207 - Fall 2020
 * @version 1.0
 * @since 1.0
 */

public class AvailableSchedulePresenter extends AbstractPresenter{
    private final AttendeeManager attendeeManager;

    /**
     * Create an AvailableSchedulePresenter
     * @param userController the UserController that contains all of the managers
     */
    public AvailableSchedulePresenter(UserController userController) {
        super(userController);
        attendeeManager = new AttendeeManager(userManager);
    }

    /**
     * view available schedule
     * @return the available schedule
     */
    public ArrayList<String[]> viewAvailableSchedules(){
        return this.availableSchedules();
    }
    // place to UI

    /**
     * Puts all of the schedules that the user is available to attend in an ArrayList of Strings.
     * @return the ArrayList of available schedule in String form
     */
    ArrayList<String[]> availableSchedules(){
        ArrayList<String[]> schedules = eventManager.viewUpcomingEvents();
        ArrayList<String> temp = new ArrayList<>();

        //activity that is full and user is not free.
        for(String[] info: schedules){
            if(checkIsConflict(info)){
                temp.add(eventManager.searchEventByUUID(info[0])[0]);
            }
        }
        schedules.removeIf(info -> temp.contains(info[0]));
        return schedules;
    }

    /**
     * Check if the schedule is conflicting with the user's schedule
     * @param info contains the information of the event
     * @return if the user is free
     */
    private boolean checkIsConflict(String[] info){
        if (!roomManager.CheckRoomFullness(eventManager.numAttendee(UUID.fromString(info[0])),
                UUID.fromString(info[4]))){
            return true;
        }
        if (eventManager.numAttendee(UUID.fromString(info[0])) >= eventManager.getEventMaxParticipant(UUID.
                fromString(info[0]))){
            return true;
        }

        LocalDateTime[] time = getTimeHelper(info);
        return !attendeeManager.isFree(time);
    }


}
