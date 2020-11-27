package Controllers;

import globallyAccessible.ActivityNotFoundException;
import globallyAccessible.NoActivitiesException;
import globallyAccessible.UserNotFoundException;
import Presenters.Presenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class RescheduleSpeakerController extends ActivityController {
    private String[] actInfo;
    private LocalDateTime[] actTime;


    public RescheduleSpeakerController(UserController userController) {
        super(userController);
        actInfo = new String[]{};
        actTime = new LocalDateTime[]{};
    }

    private String[] tmp(String actID) {
        return activityManager.searchActivityByUUID(actID);
    }

    public ArrayList<String> availableSpeakers(String actID){
        actInfo = tmp(actID);
        actTime = getTimeHelper(actInfo);
        ArrayList<String> freeSpeakers = userManager.availableSpeakers(actTime);
        freeSpeakers.add(actInfo[5]);
        return freeSpeakers;
    }

    public void updateRescheduledSpeaker(String speaker){
        activityManager.addSpeaker(UUID.fromString(actInfo[0]), speaker);
        userManager.deleteActivity(actInfo[5], actTime);
        userManager.otherAddSchedule(speaker, actTime, UUID.fromString(actInfo[0]));
    }





    public ArrayList<String[]> getAllActivities() throws NoActivitiesException {
        ArrayList<String[]> allActivities = activityManager.viewUpcommingActivites();
        if (allActivities.size() == 0){
            throw new NoActivitiesException("No activities created yet");
        }
        return allActivities;
    }

    public String checkingValidActivityID(ArrayList<String[]> allActivities, String actID)
            throws ActivityNotFoundException {
        if (! extractActIDHelper(allActivities).contains(actID)){
            throw new ActivityNotFoundException("invalid activity ID");
        }
        return actID;
    }
}
