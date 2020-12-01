package Controllers;

import entities.Activity;
import entities.User;
import globallyAccessible.UserType;
import useCases.ActivityManager;
import useCases.MessageRoomManager;
import useCases.RoomManager;
import useCases.UserManager;

import java.util.ArrayList;
import java.util.UUID;

public class ActivityController extends AbstractController{


    public ActivityController(UserController userController) {
        super(userController);
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


    /**
     * Print out the schedule of all activities this attendee can sign in.
     * Information include: topic of activity, start time and end time, ID of assigned room,
     * ID of this activity and name of speaker.
     * Specific format will be determined in Presenter.
     */


    protected <Arraylist> void cancelActivity(Arraylist<Activity>) {
        for (activity:
             ArrayList<Activity>) {
            for (user:
                 ArrayList<User>) {
                userManager.deleteActivity();
            }
        }
    }





}
