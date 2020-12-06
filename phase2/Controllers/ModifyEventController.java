package Controllers;

import globallyAccessible.NoEventsException;

import java.util.ArrayList;
import java.util.UUID;


public class ModifyEventController extends EventController {

    public ModifyEventController(UserController userController) {
        super(userController);
    }

    public ArrayList<String[]> getAllActivities() throws NoEventsException {
        ArrayList<String[]> allActivities = eventManager.viewUpcomingActivites();
        if (allActivities.size() == 0) {
            throw new NoEventsException("No activities created yet");
        }
        return allActivities;
    }

    public void changeEventMaxNumPeople(UUID activityId, Integer newMaxNum){
        eventManager.changeEventMaxParticipant(activityId, newMaxNum);
    }
}
