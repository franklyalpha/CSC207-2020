package functionalityPresenters;

import Controllers.UserController;
import useCases.AttendeeManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class AvailableSchedulePresenter extends AbstractPresenter{
    private AttendeeManager attendeeManager;

    public AvailableSchedulePresenter(UserController userController) {
        super(userController);
        attendeeManager = new AttendeeManager(userManager);
    }

    public ArrayList<String[]> viewAvailableSchedules(){
        return this.availableSchedules();
    }
    // place to UI

    ArrayList<String[]> availableSchedules(){
        ArrayList<String[]> schedules = eventManager.viewUpcomingActivities();
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
