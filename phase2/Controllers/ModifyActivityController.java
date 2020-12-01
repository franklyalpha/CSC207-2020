package Controllers;

import entities.Activity;
import globallyAccessible.NoActivitiesException;
import useCases.ActivityManager;

import java.util.ArrayList;
import java.util.UUID;


public class ModifyActivityController extends ActivityController {

    public ModifyActivityController(UserController userController) {
        super(userController);
    }

    public ArrayList<String[]> getAllActivities() throws NoActivitiesException {
        ArrayList<String[]> allActivities = activityManager.viewUpcommingActivites();
        if (allActivities.size() == 0) {
            throw new NoActivitiesException("No activities created yet");
        }
        return allActivities;
    }

    public void changeActivityMaxNumPeople(UUID activityId, Integer newMaxNum){
        activityManager.changeActivityMaxParticipant(activityId, newMaxNum);
    }
}
